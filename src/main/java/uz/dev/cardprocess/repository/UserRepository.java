package uz.dev.cardprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.cardprocess.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}