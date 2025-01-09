package uz.dev.cardprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.dev.cardprocess.entity.Transaction;
import uz.dev.cardprocess.entity.enums.TransactionType;
import uz.dev.cardprocess.projections.ResponseTrDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

   /* @Query(value = "SELECT * FROM get_transactions(:cardId, :transactionName, :currencyName, :transactionId, :externalId, :limit, :offset)",
            nativeQuery = true)
    List<ResponseTrDto> getTransactions(
            @Param("cardId") UUID cardId,
            @Param("transactionName") String transactionName,
            @Param("currencyName") String currencyName,
            @Param("transactionId") UUID transactionId,
            @Param("externalId") String externalId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );*/

    List<Transaction> findByTransactionType(TransactionType transactionType);

    Optional<List<Transaction>> findByTransactionType(String typeName);
}