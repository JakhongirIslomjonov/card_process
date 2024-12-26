package uz.dev.cardprocess.service;

import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.CreditRequestDTO;
import uz.dev.cardprocess.dto.DebitRequestDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.entity.Transaction;

@Service
public interface TransactionService {
        Transaction saveDebitTransaction(DebitRequestDTO debitDTO, long newBalance, Card card, Long exchangeRate);
        Transaction saveDebitTransaction(CreditRequestDTO creditRequestDTO, long newBalance, Card card, Long exchangeRate);
}
