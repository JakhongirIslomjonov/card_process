package uz.dev.cardprocess.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import uz.dev.cardprocess.entity.enums.Currency;
import uz.dev.cardprocess.entity.enums.TransactionPurpose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;


@Data

public class DebitRequestDTO implements Serializable {

    @JsonProperty("external_id")
    private String externalId;

    @Positive
    private Long amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Currency currency;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TransactionPurpose transactionPurpose;

    public DebitRequestDTO(String externalId, Long amount, Currency currency, TransactionPurpose transactionPurpose) {
        this.externalId = externalId;
        this.amount = amount;
        this.currency = (currency != null) ? currency : Currency.UZS;
        this.transactionPurpose = transactionPurpose;
    }
}
