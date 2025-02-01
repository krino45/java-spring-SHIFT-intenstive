package ru.cft.igoshin.core.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.cft.igoshin.core.configuration.SessionProperties;
import ru.cft.igoshin.core.repository.SessionRepository;
import ru.cft.igoshin.core.repository.TransferRepository;
import ru.cft.igoshin.core.repository.UserRepository;
import ru.cft.igoshin.core.repository.WalletRepository;

@Component
public class CommonServiceUtilFactory {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;
    private final SessionProperties sessionProperties;

    @Autowired
    public CommonServiceUtilFactory(UserRepository userRepository,
                                    SessionRepository sessionRepository,
                                    PasswordEncoder passwordEncoder,
                                    TransferRepository transferRepository,
                                    WalletRepository walletRepository,
                                    SessionProperties sessionProperties) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
        this.sessionProperties = sessionProperties;
    }

    public TransferWalletUtil createTransferWalletUtil() {
        return new TransferWalletUtil(transferRepository, walletRepository);
    }
    public UserSessionUtil createUserSessionUtil() {
        return new UserSessionUtil(userRepository, sessionRepository, sessionProperties ,passwordEncoder);
    }
}
