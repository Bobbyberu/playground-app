package com.playground.controllers;

import com.playground.model.dto.SessionDto;
import com.playground.model.entity.Session;
import com.playground.service.SessionService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<SessionDto>> getSessions() {
        List<SessionDto> sessions = sessionService.getSessions().stream()
                .map(s -> new SessionDto(s))
                .collect(Collectors.toList());
        return new ResponseEntity<>(sessions, HttpStatus.OK);
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
    public ResponseEntity<SessionDto> getSession(@PathVariable("id") int id) throws ResourceNotFoundException {
        Session session = sessionService.getSession(id);

        if (session == null) {
            throw new ResourceNotFoundException("Session with id " + id + " not found");
        }

        return new ResponseEntity<>(new SessionDto(session), HttpStatus.OK);
    }

    /**
     * [POST] Create a session and return it
     *
     * @param session Session
     *
     * @return ResponseEntity
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<SessionDto> createSession(@RequestBody Session session) {
        return new ResponseEntity<>(new SessionDto(sessionService.createSession(session)), HttpStatus.CREATED);
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
    public ResponseEntity<SessionDto> updateSession(@PathVariable("id") int id, @RequestBody Session session) throws ResourceNotFoundException {
        Session currentSession = sessionService.getSession(id);

        if (currentSession == null) {
            throw new ResourceNotFoundException("Session with id " + id + " not found");
        }

        return new ResponseEntity<>(new SessionDto(sessionService.updateSession(id, session)), HttpStatus.OK);
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
