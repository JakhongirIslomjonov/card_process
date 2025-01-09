package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import uz.dev.cardprocess.repository.UserRepository;
import uz.dev.cardprocess.service.TransactionService;
import uz.dev.cardprocess.util.CardUtil;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CardUtil cardUtil;

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
        cardUtil.checkCardExistence(cardId);
        Pageable pageable = PageRequest.of(page, size);
        List<Transaction> transactions = transactionRepository.findByTransactionType(type.name()).orElseThrow(() -> new BadRequestException("this  type not transactions : " + type.name()));
        Page<Transaction> pagedTransactions = new PageImpl<>(transactions, pageable, transactions.size());
        return new DataDTO<>(pagedTransactions);
    }

   /* @Override
    public DataDTO<Page<ResponseTrDto>> getTransaction(
            UUID cardId,
            TransactionType type,
            UUID transactionId,
            String externalId,
            Currency currency,
            int page,
            int size
    ) {

        String transactionName = type != null ? type.name() : null;
        String currencyName = currency != null ? currency.name() : null;


        int offset = page * size; // Page va size'dan offset hisoblash
        Pageable pageable = PageRequest.of(page, size);


        List<ResponseTrDto> transactions = transactionRepository.getTransactions(
                cardId,
                transactionName,
                currencyName,
                transactionId,
                externalId,
                size,
                offset
        );

        // Listni Page obyektiga o'zgartirish
        Page<ResponseTrDto> pagedTransactions = new PageImpl<>(transactions, pageable, transactions.size());

        return new DataDTO<>(pagedTransactions);
    }*/

}
