package ru.cft.igoshin.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.igoshin.api.dto.user.*;
import ru.cft.igoshin.core.service.UserService;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService _userService;

    @Autowired
    public UserController(UserService userService) {
        this._userService = userService;
    }

    @PostMapping
    public UserCreateResponse createUser(@RequestBody @Validated UserCreateRequest user) {
        log.info("Accept createUser POST request");
        return _userService.createUser(user);
    }
}
