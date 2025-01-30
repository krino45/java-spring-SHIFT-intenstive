package ru.cft.igoshin.core.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.igoshin.api.dto.user.UserCreateRequest;
import ru.cft.igoshin.api.dto.user.UserCreateResponse;
import ru.cft.igoshin.core.model.User;
import ru.cft.igoshin.core.repository.UserRepository;
import ru.cft.igoshin.core.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserCreateResponse createUser(UserCreateRequest userDTO) {
        User u = userMapper.toUser(userDTO, passwordEncoder);
        userRepository.save(u);
        return userMapper.toUserCreateResponse(u);
    }

    /*
     *  TODO:
     *   -- Literally everything else
     */
}