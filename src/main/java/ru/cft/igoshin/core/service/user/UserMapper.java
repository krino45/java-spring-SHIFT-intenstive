package ru.cft.igoshin.core.service.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.cft.igoshin.api.dto.user.UserCreateRequest;
import ru.cft.igoshin.api.dto.user.UserCreateResponse;
import ru.cft.igoshin.core.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserCreateResponse toUserCreateResponse(User user);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "hashedPassword",
            expression = "java(mapPassword(userCreateRequest.password(), passwordEncoder))")
    User toUser(UserCreateRequest userCreateRequest, PasswordEncoder passwordEncoder);

    default String mapPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }
}
