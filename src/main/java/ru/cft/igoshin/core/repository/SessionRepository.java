package ru.cft.igoshin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.igoshin.core.model.Session;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}