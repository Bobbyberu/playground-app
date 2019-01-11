package com.playground.service.interfaces;

import com.playground.model.Session;

import java.util.List;

/**
 * Interface ISessionService
 */
public interface ISessionService {

    /**
     * Return all sessions
     *
     * @return List<Session>
     */
    List<Session> getSessions();

    /**
     * Return one session
     *
     * @param id int
     *
     * @return Session
     */
    Session getSession(int id);

    /**
     * Create a session and return it
     *
     * @param session Session
     *
     * @return Session
     */
    Session createSession(Session session);

    /**
     * Update a session and return it
     *
     * @param id int
     * @param session Session
     *
     * @return Session
     */
    Session updateSession(int id, Session session);

    /**
     * Delete a session
     *
     * @param session Session
     */
    void deleteSession(Session session);
}
