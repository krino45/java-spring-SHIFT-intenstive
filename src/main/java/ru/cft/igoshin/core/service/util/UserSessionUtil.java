package ru.cft.igoshin.core.service.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.cft.igoshin.core.model.Session;
import ru.cft.igoshin.core.model.User;
import ru.cft.igoshin.core.repository.SessionRepository;
import ru.cft.igoshin.core.repository.UserRepository;
import ru.cft.igoshin.core.service.CommonServiceUtil;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

import java.util.UUID;

@Component
public class UserSessionUtil implements CommonServiceUtil {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    @Getter
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSessionUtil(UserRepository userRepository, SessionRepository sessionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validateUser(UUID userId, UUID sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new CustomServiceException("Session with specified ID ("+sessionId+") does not exist."));
        return session.getUser().getId().equals(userId);
    }
    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomServiceException("User with specified id (" + userId + ") does not exist."));
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone).orElseThrow(
                () -> new CustomServiceException("User with specified phone number (" + phone + ") does not exist."));
    }


    public Session getSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(
                () -> new CustomServiceException("Session with specified id ("+sessionId+") does not exist."));
    }

    public boolean validatePassword(User user, String password) {
        return passwordEncoder.matches(password, user.getHashedPassword());
    }
}
