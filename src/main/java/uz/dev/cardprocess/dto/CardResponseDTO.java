    package uz.dev.cardprocess.dto;

    import com.fasterxml.jackson.annotation.JsonProperty;

    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.PositiveOrZero;
    import lombok.*;
    import uz.dev.cardprocess.entity.Card;
    import uz.dev.cardprocess.entity.enums.CardStatus;
    import uz.dev.cardprocess.entity.enums.Currency;


    import java.io.Serializable;
    import java.util.UUID;

    /**
     * DTO for {@link Card}
     */
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class CardResponseDTO implements Serializable {
        @JsonProperty("card_id")
        private UUID id;
        @JsonProperty("user_id")
        private Long userId;
        @NotNull
        private CardStatus status;
        @NotNull
        @PositiveOrZero
        private Long balance;
        @NotNull
        private Currency currency;
    }

