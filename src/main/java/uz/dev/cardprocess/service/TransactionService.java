package uz.dev.cardprocess.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.CreditRequestDTO;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.DebitRequestDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.entity.Transaction;
import uz.dev.cardprocess.entity.enums.Currency;
import uz.dev.cardprocess.entity.enums.TransactionType;
import uz.dev.cardprocess.projections.ResponseTrDto;

import java.util.UUID;

@Service
public interface TransactionService {
    Transaction saveDebitTransaction(DebitRequestDTO debitDTO, long newBalance, Card card, Long exchangeRate);

    Transaction saveDebitTransaction(CreditRequestDTO creditRequestDTO, long newBalance, Card card, Long exchangeRate);

    DataDTO<?> getTransaction(UUID cardId, TransactionType type, int page, int size);
}
