package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.CreditRequestDTO;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.DebitRequestDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.entity.Transaction;
import uz.dev.cardprocess.entity.enums.TransactionType;
import uz.dev.cardprocess.exceptions.BadRequestException;
import uz.dev.cardprocess.repository.CardRepository;
import uz.dev.cardprocess.repository.TransactionRepository;
import uz.dev.cardprocess.service.TransactionService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    @Override
    public Transaction saveDebitTransaction(DebitRequestDTO debitDTO, long newBalance, Card card, Long exchangeRate) {
        Transaction transaction = Transaction.builder()
                .afterBalance(newBalance)
                .transactionType(TransactionType.DEBIT)
                .currency(debitDTO.getCurrency())
                .amount(debitDTO.getAmount())
                .card(card)
                .exchangeRate(exchangeRate)
                .externalId(debitDTO.getExternalId())
                .purpose(debitDTO.getTransactionPurpose())
                .build();
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction saveDebitTransaction(CreditRequestDTO creditRequestDTO, long newBalance, Card card, Long exchangeRate) {
        Transaction transaction = Transaction.builder()
                .afterBalance(newBalance)
                .transactionType(TransactionType.DEBIT)
                .currency(creditRequestDTO.getCurrency())
                .amount(creditRequestDTO.getAmount())
                .card(card)
                .exchangeRate(exchangeRate)
                .externalId(creditRequestDTO.getExternalId())
                .build();
        return transactionRepository.save(transaction);
    }

    @Override
    public DataDTO<?> getTransaction(UUID cardId, TransactionType type, int page, int size) {
        cardRepository.findById(cardId).orElseThrow(() -> new BadRequestException("cardId topilmadi"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> pagedTransactions = transactionRepository.findByTransactionType(type,pageable);
        return new DataDTO<>(pagedTransactions);
    }


}
