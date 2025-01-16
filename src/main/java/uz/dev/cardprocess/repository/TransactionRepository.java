package uz.dev.cardprocess.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.cardprocess.entity.Transaction;
import uz.dev.cardprocess.entity.enums.TransactionType;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findByTransactionType(TransactionType type, Pageable pageable);
}
