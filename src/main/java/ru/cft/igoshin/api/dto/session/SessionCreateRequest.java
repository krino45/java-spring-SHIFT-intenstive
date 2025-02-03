package ru.cft.igoshin.api.dto.session;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SessionCreateRequest(
        @NotNull
        UUID userId,

        @NotNull
        String password
){ }
