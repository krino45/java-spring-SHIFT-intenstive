package ru.cft.igoshin.core.service.wallet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.igoshin.api.dto.wallet.WalletResponse;
import ru.cft.igoshin.core.model.Wallet;
import ru.cft.igoshin.core.repository.WalletRepository;
import ru.cft.igoshin.core.service.CommonServiceUtil;
import ru.cft.igoshin.core.service.CommonServiceUtilFactory;
import ru.cft.igoshin.core.service.util.UserSessionUtil;
import ru.cft.igoshin.core.service.WalletService;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

import java.util.UUID;

@Transactional
@Service
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final CommonServiceUtilFactory commonServiceUtilFactory;
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Autowired
    public WalletServiceImpl(CommonServiceUtilFactory commonServiceUtilFactory, WalletRepository walletRepository, WalletMapper walletMapper) {
        this.commonServiceUtilFactory = commonServiceUtilFactory;
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }


    @Override
    public WalletResponse getWalletById(UUID userId, UUID sessionId) {
        UserSessionUtil userSessionUtil = commonServiceUtilFactory.createUserSessionUtil();
        if(userSessionUtil.validateUser(userId, sessionId)) {
            return walletMapper
                    .toWalletResponse(
                            walletRepository
                            .findByUser_Id(userId)
                            .orElseThrow(
                                    ()-> new CustomServiceException("Wallet/User doesn't exist")
                            )
                    );
        }
        throw new CustomServiceException("UserID/sessionID mismatch");
    }

    @Override
    public void hesoyam(UUID userId, UUID sessionId) {
        UserSessionUtil userSessionUtil = commonServiceUtilFactory.createUserSessionUtil();
        if(userSessionUtil.validateUser(userId, sessionId)) {
            Wallet wallet = walletRepository.findByUser_Id(userId).orElseThrow(
                    ()-> new CustomServiceException("Wallet/User doesn't exist"));
            if(Math.random() <= 0.25) {
                log.info("BOOOM! BOOOM! BOOOOOOOOOM!!!!!");
                wallet.setBalance(wallet.getBalance() + 10);
                walletRepository.save(wallet);
            } else {
                log.info("99% percent of gamblers quit before they hit big....");
            }
        } else {
            throw new CustomServiceException("UserID/sessionID mismatch");
        }
    }
}
