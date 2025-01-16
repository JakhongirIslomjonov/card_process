package uz.dev.cardprocess.entity;


import jakarta.persistence.*;
import lombok.*;
import uz.dev.cardprocess.entity.base.Auditable;
import uz.dev.cardprocess.entity.enums.CardStatus;
import uz.dev.cardprocess.entity.enums.Currency;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "card")
public class Card extends Auditable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    private Long balance;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Version
    private int version;
}
