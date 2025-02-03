package ru.cft.igoshin.api.dto.transfer;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TransferCreateRequest(
        Long walletId,

        @Pattern(regexp = "^7\\d{10}$")
        String phone,

        @NotNull
        @Min(1)
        Long amount
) {
    @AssertTrue
    private boolean isOnlyWalletIdOrOnlyPhoneProvided() {
        return (walletId == null && phone != null) || (walletId != null && phone == null);
    }
}
