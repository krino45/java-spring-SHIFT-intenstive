package ru.cft.igoshin.api.dto.session;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionResponse(
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime expirationTime,

        @NotNull
        UUID sessionId,

        @NotNull
        boolean active
        ) {
}
