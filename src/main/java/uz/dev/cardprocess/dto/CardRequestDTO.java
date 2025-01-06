package uz.dev.cardprocess.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import uz.dev.cardprocess.entity.enums.CardStatus;
import uz.dev.cardprocess.entity.enums.Currency;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link uz.dev.cardprocess.entity.Card}
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CardRequestDTO implements Serializable {
    @JsonProperty("user_id")
    private Long userId;
    private CardStatus status;
    @NotNull
    @JsonProperty("initial_amount")
    @Max(value = 10_000)
    private Long balance;
    private Currency currency;
}