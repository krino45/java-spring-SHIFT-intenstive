package ru.cft.igoshin.core.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record Session(
        UUID sessionToken,
        UUID userId,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime creationDate,
        boolean isAlive,
        LocalDateTime TTL
) {
}
