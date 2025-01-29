package ru.cft.igoshin.core.service;

import ru.cft.igoshin.api.dto.user.UserCreateRequest;
import ru.cft.igoshin.api.dto.user.UserCreateResponse;

public interface UserService {
    UserCreateResponse createUser(UserCreateRequest user);
}

