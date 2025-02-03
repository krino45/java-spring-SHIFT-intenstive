package ru.cft.igoshin.api.dto.user;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserCreateResponse(
        @NotNull
        UUID id
) {
}
