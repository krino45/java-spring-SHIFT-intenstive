package ru.cft.igoshin.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.igoshin.api.dto.user.*;
import ru.cft.igoshin.core.service.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserCreateResponse createUser(@RequestBody @Valid UserCreateRequest user) {
        log.info("Accept createUser POST request");
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public UserGetResponse getUserById(@PathVariable UUID userId,
                                       @RequestHeader("Authorization") UUID sessionId) {
        log.info("Received getUserById request for userId: {}", userId);
            return userService.getUserById(userId, sessionId);
    }

    @PatchMapping("/{userId}")
    public void updateUser(@PathVariable UUID userId,
                                      @RequestHeader("Authorization") UUID sessionId,
                                      @RequestBody @Validated UserPatchRequest user) {
        log.info("Received updateUser request for userId: {}", userId);
        userService.updateUser(userId, sessionId, user);
    }
}
