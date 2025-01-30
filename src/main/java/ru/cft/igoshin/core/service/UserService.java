package ru.cft.igoshin.core.service;

import ru.cft.igoshin.api.dto.user.*;
import ru.cft.igoshin.core.model.User;

import java.util.UUID;

public interface UserService {
    UserCreateResponse createUser(UserCreateRequest user);
    UserGetResponse getUser(UUID userId, UUID sessionId);

    User findUserById(UUID userId);
    boolean validatePassword(User user, String password);
}

