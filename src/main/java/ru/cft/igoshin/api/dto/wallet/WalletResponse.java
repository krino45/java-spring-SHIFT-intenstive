package ru.cft.igoshin.api.dto.wallet;

import jakarta.validation.constraints.NotNull;

public record WalletResponse(
        @NotNull
        Long number,
        @NotNull
        Long balance
) {
}
