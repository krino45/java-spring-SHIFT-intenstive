package ru.cft.igoshin.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.cft.igoshin.core.repository.SessionRepository;
import ru.cft.igoshin.core.repository.TransferRepository;
import ru.cft.igoshin.core.repository.UserRepository;
import ru.cft.igoshin.core.repository.WalletRepository;
import ru.cft.igoshin.core.service.util.TransferWalletUtil;
import ru.cft.igoshin.core.service.util.UserSessionUtil;

@Component
public class CommonServiceUtilFactory {
    // im premature optimizationing all over the screen we're SO back
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public CommonServiceUtilFactory(UserRepository userRepository,
                                    SessionRepository sessionRepository,
                                    PasswordEncoder passwordEncoder,
                                    TransferRepository transferRepository,
                                    WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;

    }

    public TransferWalletUtil createTransferWalletUtil() {
        return new TransferWalletUtil(transferRepository, walletRepository);
    }
    public UserSessionUtil createUserSessionUtil() {
        return new UserSessionUtil(userRepository, sessionRepository, passwordEncoder);
    }
}
