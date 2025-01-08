package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.CreditRequestDTO;
import uz.dev.cardprocess.dto.DebitRequestDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.entity.Transaction;
import uz.dev.cardprocess.entity.enums.TransactionType;
import uz.dev.cardprocess.repository.TransactionRepository;
import uz.dev.cardprocess.service.TransactionService;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

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
}
