package uz.dev.cardprocess.projections;


import lombok.Data;

import java.util.UUID;
@Data
public class ResponseTrDto {
    private UUID transactionId;
    private String externalId;
    private UUID cardId;
    private Long amount;
    private Long afterBalance;
    private String currency;
    private String transactionType;
    private String purpose;
    private Long exchangeRate;
}

