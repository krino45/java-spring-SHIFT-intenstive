package ru.cft.igoshin.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cft.igoshin.api.dto.session.SessionCreateRequest;
import ru.cft.igoshin.api.dto.session.SessionResponse;
import ru.cft.igoshin.api.dto.wallet.WalletResponse;
import ru.cft.igoshin.core.service.SessionService;
import ru.cft.igoshin.core.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
@Slf4j
public class WalletController {

    private final WalletService walletService;
    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}")
    public WalletResponse getWalletById(@PathVariable UUID userId,
                                        @RequestHeader("Authorization") UUID sessionId) {
        return walletService.getWalletById(userId, sessionId);
    }

    @PostMapping("/{userId}/HESOYAM")
    public void hesoyam(@PathVariable UUID userId,
                                        @RequestHeader("Authorization") UUID sessionId) {
        walletService.hesoyam(userId, sessionId);
    }

}
