package ru.cft.igoshin.core.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.igoshin.core.model.Session;
import ru.cft.igoshin.core.model.User;
import ru.cft.igoshin.core.repository.SessionRepository;
import ru.cft.igoshin.core.repository.UserRepository;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    @Getter
    private PasswordEncoder passwordEncoder;

    public boolean validateUser(UUID userId, UUID sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomServiceException("Session with specified ID ("+sessionId+") does not exist."));
        return session.getUser().getId().equals(userId);
    }
    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomServiceException("User with specified id (" + userId + ") does not exist."));
    }

    public boolean validatePassword(User user, String password) {
        return passwordEncoder.matches(password, user.getHashedPassword());
    }
}
