package uz.dev.cardprocess.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import uz.dev.cardprocess.entity.base.Auditable;
import uz.dev.cardprocess.entity.enums.RoleName;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles")
public class Role extends Auditable implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
