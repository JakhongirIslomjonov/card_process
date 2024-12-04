package uz.dev.cardprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.cardprocess.entity.Role;
import uz.dev.cardprocess.entity.enums.RoleName;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRoleName(RoleName roleName);
}
