package uz.dev.cardprocess.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import uz.dev.cardprocess.entity.enums.Currency;

@Getter
@Setter
public class CreditRequestDTO {
    @JsonProperty("external_id")
    private String externalId;

    @Positive
    private Long amount;

    private Currency currency;  

    public CreditRequestDTO(String externalId, Long amount, Currency currency) {
        this.externalId = externalId;
        this.amount = amount;
        this.currency = (currency != null) ? currency : Currency.UZS;
    }
}
