package ru.cft.igoshin.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.igoshin.core.models.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}