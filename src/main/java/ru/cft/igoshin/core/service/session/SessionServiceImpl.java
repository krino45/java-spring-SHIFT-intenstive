package ru.cft.igoshin.core.service.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.igoshin.api.dto.session.SessionCreateRequest;
import ru.cft.igoshin.api.dto.session.SessionResponse;
import ru.cft.igoshin.core.configuration.SessionProperties;
import ru.cft.igoshin.core.model.Session;
import ru.cft.igoshin.core.model.User;
import ru.cft.igoshin.core.repository.SessionRepository;
import ru.cft.igoshin.core.service.SessionService;
import ru.cft.igoshin.core.service.UserService;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

import java.util.UUID;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionMapper sessionMapper;
    @Autowired
    private SessionProperties sessionProperties;
    @Autowired
    private UserService userService;

    @Override
    public SessionResponse createSession(SessionCreateRequest request) throws CustomServiceException {
        UUID userId = request.userId();
        String password = request.password();
        User user;
        try {
            user = userService.findUserById(userId);
        } catch (CustomServiceException e) {
            log.warn("Invalid userId provided: {}", userId);
            throw new CustomServiceException("Invalid credentials");
        }

        if (userService.validatePassword(user, password)) {
            Session session = Session.builder()
                    .ttl(sessionProperties.getTtl())
                    .user(user)
                    .build();
            sessionRepository.save(session);
            return sessionMapper.toSessionCreateResponse(session);
        } else {
            log.warn("Invalid password provided: {}", password);
            throw new CustomServiceException("Invalid credentials");
        }
    }

    @Override
    public SessionResponse getSessionById(UUID sessionId) {
        Session session = sessionRepository.getSessionBySessionId(sessionId)
                .orElseThrow(() -> new CustomServiceException("No such session exists."));
        return sessionMapper.toSessionCreateResponse(session);
    }

    @Override
    public void closeSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
