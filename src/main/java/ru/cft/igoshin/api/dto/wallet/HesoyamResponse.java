package ru.cft.igoshin.api.dto.wallet;

import jakarta.validation.constraints.NotNull;

public record HesoyamResponse(
        @NotNull
        String message
) {
}
