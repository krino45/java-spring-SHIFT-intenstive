package ru.cft.igoshin.core.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record MoneyTransfer(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime transferDate,
        int transferAmount,
        Timestamp createdAt,
        int senderWallet,
        int recipientWallet
) {
}
