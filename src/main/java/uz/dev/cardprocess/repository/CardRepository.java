package uz.dev.cardprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.cardprocess.entity.Card;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
}