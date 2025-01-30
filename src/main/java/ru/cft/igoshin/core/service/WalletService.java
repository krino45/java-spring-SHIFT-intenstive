package ru.cft.igoshin.core.service;

import ru.cft.igoshin.api.dto.wallet.WalletResponse;

import java.util.UUID;

public interface WalletService {
    WalletResponse getWalletById(UUID userId, UUID sessionId);
    void hesoyam(UUID userId, UUID sessionId);
}
