package uz.dev.cardprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.dev.cardprocess.entity.Card;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    @Query("""
            select count (Card ) from Card 
                        where  status ='ACTIVE' and user.id =:userId""")
    int findActiveCardByUserId(Long userId);
}