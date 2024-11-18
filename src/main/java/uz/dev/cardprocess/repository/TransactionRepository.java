package uz.dev.cardprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.cardprocess.entity.Transaction;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}