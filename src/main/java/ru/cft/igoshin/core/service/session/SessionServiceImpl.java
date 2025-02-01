package ru.cft.igoshin.core.service.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.igoshin.api.dto.session.SessionCreateRequest;
import ru.cft.igoshin.api.dto.session.SessionResponse;
import ru.cft.igoshin.core.configuration.SessionProperties;
import ru.cft.igoshin.core.model.Session;
import ru.cft.igoshin.core.model.User;
import ru.cft.igoshin.core.repository.SessionRepository;
import ru.cft.igoshin.core.service.util.CommonServiceUtilFactory;
import ru.cft.igoshin.core.service.util.UserSessionUtil;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

import java.util.UUID;

@Transactional
@Slf4j
@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final SessionProperties sessionProperties;
    private final CommonServiceUtilFactory commonServiceUtilFactory;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, SessionMapper sessionMapper,
                              SessionProperties sessionProperties, CommonServiceUtilFactory commonServiceUtilFactory) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.sessionProperties = sessionProperties;
        this.commonServiceUtilFactory = commonServiceUtilFactory;
    }

    @Override
    public SessionResponse createSession(SessionCreateRequest request) throws CustomServiceException {
        UserSessionUtil userSessionUtil = commonServiceUtilFactory.createUserSessionUtil();

        UUID userId = request.userId();
        String password = request.password();
        User user;
        try {
            user = userSessionUtil.findUserById(userId);
        } catch (CustomServiceException e) {
            log.warn("Invalid userId provided: {}", userId);
            throw new CustomServiceException("Invalid credentials");
        }

        if (userSessionUtil.validatePassword(user, password)) {
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
        Session session = sessionRepository.findById((sessionId))
                .orElseThrow(() -> new CustomServiceException("No such session exists."));
        return sessionMapper.toSessionCreateResponse(session);
    }

    @Override
    public void closeSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
