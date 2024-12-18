package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.*;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.entity.IdempotencyRecord;
import uz.dev.cardprocess.entity.Transaction;
import uz.dev.cardprocess.entity.enums.CardStatus;
import uz.dev.cardprocess.exceptions.BadRequestException;
import uz.dev.cardprocess.mapper.CardMapper;
import uz.dev.cardprocess.mapper.DebitMapper;
import uz.dev.cardprocess.repository.CardRepository;
import uz.dev.cardprocess.repository.IdempotencyRecordRepository;
import uz.dev.cardprocess.repository.TransactionRepository;
import uz.dev.cardprocess.repository.UserRepository;
import uz.dev.cardprocess.service.CardService;
import uz.dev.cardprocess.util.CardUtil;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final IdempotencyRecordRepository idempotencyRecordRepository;
    private final CardUtil cardUtil;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;
    private final String idempotencyKey = UUID.randomUUID().toString();
    private final TransactionRepository transactionRepository;
    private final DebitMapper debitMapper;

    @Override
    public DataDTO<CardResponseDTO> createCard(UUID idempotencyKey, CardRequestDTO cardRequestDTO) {
        if (userRepository.findById(cardRequestDTO.getUserId()).isEmpty()) {
            throw new BadRequestException("user not found");
        }
        if (idempotencyRecordRepository.findById(idempotencyKey).isPresent()) {
            Card card = cardUtil.checkCardExistence(idempotencyRecordRepository.findById(idempotencyKey).get().getCardId());
            return new DataDTO<>(cardMapper.toDto(card));
        }
        if (cardRepository.findActiveCardByUserId(cardRequestDTO.getUserId()) == 3) {
            throw new BadRequestException("The card limit has been exceeded ");
        }
        Card card = cardRepository.save(cardMapper.toEntity(cardRequestDTO));
        idempotencyRecordRepository.save(new IdempotencyRecord(idempotencyKey, card.getId()));
        return new DataDTO<>(cardMapper.toDto(card));
    }

    @Override
    public ResponseEntity<?> getCardById(UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new BadRequestException("Card not found by this id: " + cardId));
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(String.valueOf(idempotencyKey))
                .body(cardMapper.toDto(card));
    }

    @Override
    public DataDTO<String> blockCard(String eTag, UUID cardId) {
        Card card = checkEtag(eTag, cardId);
        checkStatus(card);
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
        return new DataDTO<>("card block");

    }

    @Override
    public DataDTO<String> unBlockCard(String eTag, UUID cardId) {
        Card card = checkEtag(eTag, cardId);
        checkStatusUnBlock(card);
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
        return new DataDTO<>("card unBlock");
    }

    @Override
    public DataDTO<DebitResponseDTO> debitCard(UUID idempotencyKey, DebitRequestDTO debitRequestDTO, UUID cardId) {

        checkStatus(cardRepository.findById(cardId).get());
        if (idempotencyRecordRepository
                .findById(idempotencyKey).isPresent()) {
            var transaction = transactionRepository
                    .findById(idempotencyRecordRepository
                            .findById(idempotencyKey).get().getTransactionId());
            if (transaction.isPresent()) {
                return new DataDTO<>(debitMapper.toDebitResponseDto(transaction.get()));
            }
        }
        Card card = cardRepository
                .findById(idempotencyRecordRepository
                        .findById(idempotencyKey).get().getCardId()).orElseThrow(() -> new BadRequestException("card not found "));
        checkBalanceAndWithdraw(card, debitRequestDTO);


    }

    private Transaction checkBalanceAndWithdraw(Card card, DebitRequestDTO debitRequestDTO) {
        if (card.getCurrency().equals(debitRequestDTO.getCurrency()) && card.getBalance() < debitRequestDTO.getAmount()) {
            throw new BadRequestException("Mablag'da hatolik");
        }
        if (!card.getCurrency().equals(debitRequestDTO.getCurrency())) {
            long fetchCurrencyRate = cardUtil.fetchCurrencyRate();
            long amountCardCurrency;
            amountCardCurrency = cardUtil.sumWithCurrencyRate(card,debitRequestDTO,fetchCurrencyRate);

         long resultBalance=card.getBalance()-amountCardCurrency;
         card.setBalance(resultBalance);
         cardRepository.save(card);
         return

        }
    }

    private void checkStatusUnBlock(Card card) {
        if (!card.getStatus().equals(CardStatus.BLOCKED)) throw new BadRequestException(" card is not Block");
    }

    private void checkStatus(Card card) {
        if (!card.getStatus().equals(CardStatus.ACTIVE)) throw new BadRequestException(" card is not  Active");
    }

    private Card checkEtag(String eTag, UUID cardId) {
        Card card = cardUtil.checkCardExistence(cardId);
        if (!(idempotencyKey.equals(eTag))) throw new BadRequestException("ETag does not match");
        return card;
    }
}
