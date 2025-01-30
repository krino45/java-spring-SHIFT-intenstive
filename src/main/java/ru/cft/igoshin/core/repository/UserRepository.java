package ru.cft.igoshin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.igoshin.core.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}