package uz.dev.cardprocess.entity.base;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class BaseAuditable implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
}
