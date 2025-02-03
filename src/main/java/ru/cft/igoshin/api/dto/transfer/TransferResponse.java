package ru.cft.igoshin.api.dto.transfer;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransferResponse(
        UUID transferId,
        LocalDateTime creationTime,
        Long amount,
        Long senderWalletId,
        Long recipientWalletId
) {
}
