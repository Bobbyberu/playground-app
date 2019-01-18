package com.playground.service;

import com.playground.model.Session;
import com.playground.repository.SessionRepository;
import com.playground.service.interfaces.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class SessionService
 */
@Service
public class SessionService implements ISessionService {

    /** SessionRepository sessionRepository */
    private final SessionRepository sessionRepository;

    /**
     * SessionService Constructor
     *
     * @param sessionRepository SessionRepository
     */
    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> getSessions() {
        List<Session> sessions = new ArrayList<>();
        sessionRepository.findAll().forEach(sessions::add);

        return sessions;
    }

    @Override
    public Session getSession(int id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Override
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session updateSession(int id, Session session) {
        session.setId(id);
        return sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }
}
