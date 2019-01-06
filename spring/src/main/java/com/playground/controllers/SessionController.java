package com.playground.controllers;

import com.playground.model.Session;
import com.playground.repository.SessionRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping(value = "/", produces = "application/json")
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Session> getSessionsById(@PathVariable(value = "id") int sessionId) throws ResourceNotFoundException {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session with id " + sessionId + " not found"));
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Session createSession(@Valid @RequestBody Session session) {
        return sessionRepository.save(session);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable(value = "id") int sessionId, @Valid @RequestBody Session sessionDetails)
            throws ResourceNotFoundException {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session with id " + sessionId + " not found"));
        session.setName(sessionDetails.getName());
        session.setDate(sessionDetails.getDate());
        session.setMaxPLayers(sessionDetails.getMaxPLayers());
        session.setSport(sessionDetails.getSport());
        session.setPrivate(sessionDetails.isPrivate());
        session.setPlayground(sessionDetails.getPlayground());
        session.setParticipants(sessionDetails.getParticipants());
        final Session updatedSession = sessionRepository.save(session);
        return ResponseEntity.ok(updatedSession);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSession(@PathVariable(value = "id") int sessionId) throws ResourceNotFoundException {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session with id " + sessionId + " not found"));
        sessionRepository.delete(session);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}