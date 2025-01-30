package ru.cft.igoshin.core.service.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.cft.igoshin.api.dto.user.AuthorizedUserGetResponse;
import ru.cft.igoshin.api.dto.user.UserCreateRequest;
import ru.cft.igoshin.api.dto.user.UserCreateResponse;
import ru.cft.igoshin.api.dto.user.UserGetResponse;
import ru.cft.igoshin.core.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserCreateResponse toUserCreateResponse(User user);
    UserGetResponse toUserGetResponse(User user);
    AuthorizedUserGetResponse toAuthorizedUserGetResponse(User user);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "hashedPassword",
            expression = "java(mapPassword(userCreateRequest.password(), passwordEncoder))")
    User toUser(UserCreateRequest userCreateRequest, PasswordEncoder passwordEncoder);

    default String mapPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }
}
