package uz.dev.cardprocess.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import uz.dev.cardprocess.entity.enums.Currency;
import uz.dev.cardprocess.entity.enums.TransactionPurpose;
import uz.dev.cardprocess.entity.enums.TransactionType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link uz.dev.cardprocess.entity.Transaction}
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DebitResponseDTO implements Serializable {
    @JsonProperty("transaction_id")
    private UUID id;
    @JsonProperty("external_id")
    private String externalId;
    @JsonProperty("card_id")
    private UUID cardId;
    @JsonProperty("after_balance")
    private Long afterBalance;
    private Long amount;
    private Currency currency;
    private TransactionPurpose purpose;
    @JsonProperty("exchange_rate")
    private Long exchangeRate;
}