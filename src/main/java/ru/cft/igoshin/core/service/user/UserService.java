package ru.cft.igoshin.core.service.user;

import ru.cft.igoshin.api.dto.user.*;

import java.util.UUID;

public interface UserService {
    UserCreateResponse createUser(UserCreateRequest user);
    UserGetResponse getUserById(UUID userId, UUID sessionId);
    void updateUser(UUID userId, UUID sessionId, UserPatchRequest user);
}

