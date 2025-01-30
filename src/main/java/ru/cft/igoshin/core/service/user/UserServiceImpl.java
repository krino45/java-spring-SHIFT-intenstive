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
import ru.cft.igoshin.core.service.UserService;
import ru.cft.igoshin.core.service.AuthService;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthService authService;

    @Override
    public UserCreateResponse createUser(UserCreateRequest userDTO) {
        User u = userMapper.toUser(userDTO, authService.getPasswordEncoder());
        Wallet wallet = Wallet.builder().balance(100).user(u).build();
        u.setWallet(wallet);
        userRepository.save(u);
        return userMapper.toUserCreateResponse(u);
    }

    @Override
    @Transactional
    public UserGetResponse getUserById(UUID userId, UUID sessionId) {
        User user = authService.findUserById(userId);
        if (authService.validateUser(userId, sessionId)) {
            return userMapper.toAuthorizedUserGetResponse(user);
        } else {
            return userMapper.toUserGetResponse(user);
        }
    }

    @Override
    @Transactional
    public void updateUser(UUID userId, UUID sessionId, UserPatchRequest new_user) {
        if (authService.validateUser(userId, sessionId)) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomServiceException("No such user exists"));
            if (new_user.firstName() != null)
                user.setFirstName(new_user.firstName());
            if (new_user.lastName() != null)
                user.setLastName(new_user.lastName());
            if (new_user.middleName() != null)
                user.setMiddleName(new_user.middleName());
            if (new_user.birthdate() != null)
                user.setBirthdate(new_user.birthdate());
            userRepository.save(user);
        } else {
            throw new CustomServiceException("userId / sessionId mismatch");
        }
    }
}