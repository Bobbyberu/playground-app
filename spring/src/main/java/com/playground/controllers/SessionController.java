package com.playground.controllers;

import com.playground.model.Session;
import com.playground.service.SessionService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class SessionController
 */
@RestController
@RequestMapping("/sessions")
public class SessionController {

    /** SessionService sessionService */
    private final SessionService sessionService;

    /**
     * SessionController Constructor
     *
     * @param sessionService SessionService
     */
    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * [GET] Return all sessions
     *
     * @return ResponseEntity
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Session>> getSessions() {
        return new ResponseEntity<>(sessionService.getSessions(), HttpStatus.OK);
    }

    /**
     * [GET] Return one session by id
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Session not found
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Session> getSession(@PathVariable("id") int id) throws ResourceNotFoundException {
        Session session = sessionService.getSession(id);

        if (session == null) {
            throw new ResourceNotFoundException("Session with id " + id + " not found");
        }

        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    /**
     * [POST] Create a session and return it
     *
     * @param session Session
     *
     * @return ResponseEntity
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        return new ResponseEntity<>(sessionService.createSession(session), HttpStatus.CREATED);
    }

    /**
     * [PUT] Update a session and return it
     *
     * @param id int
     * @param session Session
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Session not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable("id") int id, @RequestBody Session session) throws ResourceNotFoundException {
        Session currentSession = sessionService.getSession(id);

        if (currentSession == null) {
            throw new ResourceNotFoundException("Session with id " + id + " not found");
        }

        return new ResponseEntity<>(sessionService.updateSession(id, session), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a session
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Session not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable("id") int id) throws ResourceNotFoundException {
        Session currentSession = sessionService.getSession(id);

        if (currentSession == null) {
            throw new ResourceNotFoundException("Session with id " + id + " not found");
        }

        sessionService.deleteSession(currentSession);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
