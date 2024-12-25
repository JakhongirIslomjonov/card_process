package uz.dev.cardprocess.dto;

import lombok.Value;
import uz.dev.cardprocess.entity.enums.Currency;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link uz.dev.cardprocess.entity.Transaction}
 */
@Value
public class CreditResponseDTO implements Serializable {
    UUID id;
    String externalId;
    UUID cardId;
    Long afterBalance;
    Long amount;
    Currency currency;
    Long exchangeRate;
}