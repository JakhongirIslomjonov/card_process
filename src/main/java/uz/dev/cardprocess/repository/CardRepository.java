package uz.dev.cardprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.cardprocess.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}