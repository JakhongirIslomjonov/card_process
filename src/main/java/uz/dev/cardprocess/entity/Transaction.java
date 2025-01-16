package uz.dev.cardprocess.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.dev.cardprocess.entity.base.Auditable;
import uz.dev.cardprocess.entity.enums.Currency;
import uz.dev.cardprocess.entity.enums.TransactionType;
import uz.dev.cardprocess.entity.enums.TransactionPurpose;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "transaction")
public class Transaction extends Auditable {

    private String externalId;


    @JoinColumn(name = "card_id")
    @ManyToOne
    private Card card;
    @Column(name = "after_balance")
    private Long afterBalance;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionPurpose purpose;

    @Column(name = "exchange_rate")
    private Long exchangeRate;



}
