package uz.dev.cardprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.cardprocess.entity.IdempotencyRecord;

import java.util.UUID;

public interface IdempotencyRecordRepository extends JpaRepository<IdempotencyRecord, UUID> {
}