package uz.dev.cardprocess.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "idempotency_record")
public class IdempotencyRecord {
    @Id
    private UUID idempotencyKey;
    private UUID cardId;
    private UUID transactionId;

    public IdempotencyRecord(UUID idempotencyKey, UUID cardId) {
        this.idempotencyKey = idempotencyKey;
        this.cardId = cardId;
    }
}
