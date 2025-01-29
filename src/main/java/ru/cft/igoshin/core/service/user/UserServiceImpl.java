package ru.cft.igoshin.core.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.igoshin.api.dto.user.UserCreateRequest;
import ru.cft.igoshin.api.dto.user.UserCreateResponse;
import ru.cft.igoshin.core.models.User;
import ru.cft.igoshin.core.repositories.UserRepository;
import ru.cft.igoshin.core.service.UserService;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserCreateResponse createUser(UserCreateRequest userDTO) {
        User u = User.builder()
                .lastName(userDTO.lastName())
                .firstName(userDTO.firstName())
                .middleName(userDTO.middleName())
                .phone(userDTO.phone())
                .email(userDTO.email())
                .birthdate(userDTO.birthdate())
                .hashedPassword(userDTO.password())
                .created(new Timestamp(System.currentTimeMillis()))
                .updated(new Timestamp(System.currentTimeMillis()))
                .build();
        userRepository.save(u);
        return new UserCreateResponse(u.getId());
    }

    /*
     *  TODO:
     *  ---- Password hashing
     *  ---- Literally everything else
     *
     */
}