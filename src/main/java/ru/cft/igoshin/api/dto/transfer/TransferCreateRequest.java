package ru.cft.igoshin.api.dto.transfer;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransferCreateRequest(
        Long wallet_id,
        String phone,

        @NotNull
        @Min(1)
        Long amount
) {
//    @AssertTrue
//    private boolean isWallet_idOrPhoneExists() {
//        return wallet_id != 0;
//    }
}
