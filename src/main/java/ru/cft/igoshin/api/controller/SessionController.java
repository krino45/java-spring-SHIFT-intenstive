package ru.cft.igoshin.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cft.igoshin.api.dto.session.SessionCreateRequest;
import ru.cft.igoshin.api.dto.session.SessionResponse;
import ru.cft.igoshin.core.service.SessionService;

import java.util.UUID;

@RestController
@RequestMapping("/sessions")
@Slf4j
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    SessionResponse createSession(@RequestBody SessionCreateRequest request) {
        log.info("Received a create session request from userId: {}", request.userId());
        return sessionService.createSession(request);
    }

    @GetMapping("/{sessionId}")
    SessionResponse getSessionById(@PathVariable UUID sessionId) {
        log.info("Received a get session request for sessionId: {}", sessionId);
        return sessionService.getSessionById(sessionId);
    }

    @DeleteMapping("/{sessionId}")
    void closeSession(@PathVariable UUID sessionId) {
        log.info("Received a close session request for session: {}", sessionId);
        sessionService.closeSession(sessionId);
    }
    
}
