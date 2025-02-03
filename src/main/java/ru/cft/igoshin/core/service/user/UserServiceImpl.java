package ru.cft.igoshin.core.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.igoshin.api.dto.user.UserCreateRequest;
import ru.cft.igoshin.api.dto.user.UserCreateResponse;
import ru.cft.igoshin.api.dto.user.UserGetResponse;
import ru.cft.igoshin.api.dto.user.UserPatchRequest;
import ru.cft.igoshin.core.model.User;
import ru.cft.igoshin.core.model.Wallet;
import ru.cft.igoshin.core.repository.UserRepository;
import ru.cft.igoshin.core.service.util.CommonServiceUtilFactory;
import ru.cft.igoshin.core.service.util.UserSessionUtil;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CommonServiceUtilFactory commonServiceUtilFactory;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, CommonServiceUtilFactory commonServiceUtilFactory) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.commonServiceUtilFactory = commonServiceUtilFactory;
    }

    @Override
    public UserCreateResponse createUser(UserCreateRequest userDTO) {
        UserSessionUtil userSessionUtil = commonServiceUtilFactory.createUserSessionUtil();
        String hashedPassword = userSessionUtil.getPasswordEncoder().encode(userDTO.password());
        User u = userMapper.toUser(userDTO, hashedPassword);
        Wallet wallet = Wallet.builder().balance(100L).user(u).build();
        u.setWallet(wallet);
        userRepository.save(u);
        return userMapper.toUserCreateResponse(u);
    }

    @Override
    public UserGetResponse getUserById(UUID userId, UUID sessionId) {
        UserSessionUtil userSessionUtil = commonServiceUtilFactory.createUserSessionUtil();

        if (userSessionUtil.isSessionExpired(sessionId)) {
            throw new CustomServiceException("Session expired.");
        }
        User user = userSessionUtil.findUserById(userId);
        if (userSessionUtil.validateUser(userId, sessionId)) {
            return userMapper.toAuthorizedUserGetResponse(user);
        } else {
            return userMapper.toUserGetResponse(user);
        }
    }

    @Override
    public void updateUser(UUID userId, UUID sessionId, UserPatchRequest newUser) {
        UserSessionUtil userSessionUtil = commonServiceUtilFactory.createUserSessionUtil();
        if (userSessionUtil.isSessionExpired(sessionId)) {
            throw new CustomServiceException("Session expired.");
        }
        if (userSessionUtil.validateUser(userId, sessionId)) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomServiceException("No such user exists"));
            if (newUser.firstName() != null)
                user.setFirstName(newUser.firstName());
            if (newUser.lastName() != null)
                user.setLastName(newUser.lastName());
            if (newUser.middleName() != null)
                user.setMiddleName(newUser.middleName());
            if (newUser.birthdate() != null)
                user.setBirthdate(newUser.birthdate());
            userRepository.save(user);
        } else {
            throw new CustomServiceException("userId / sessionId mismatch");
        }
    }
}