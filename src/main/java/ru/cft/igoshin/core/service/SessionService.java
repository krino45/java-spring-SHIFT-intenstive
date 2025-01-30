package ru.cft.igoshin.core.service;

import ru.cft.igoshin.api.dto.session.*;

import java.util.UUID;

public interface SessionService {
    SessionResponse createSession(SessionCreateRequest request);
    SessionResponse getSessionById(UUID sessionId);
    void closeSession(UUID sessionId);
}
