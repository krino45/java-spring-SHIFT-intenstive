package ru.cft.igoshin.core.models;

import java.util.UUID;

public record Wallet(
        int number,
        UUID userId,
        int balance
) {
}
