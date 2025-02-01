package ru.cft.igoshin.core.service.wallet;

import ru.cft.igoshin.api.dto.wallet.HesoyamResponse;
import ru.cft.igoshin.api.dto.wallet.WalletResponse;

import java.util.UUID;

public interface WalletService {
    WalletResponse getWalletById(UUID userId, UUID sessionId);
    HesoyamResponse hesoyam(UUID userId, UUID sessionId);
}
