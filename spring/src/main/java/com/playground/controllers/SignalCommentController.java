package com.playground.controllers;

import com.playground.model.SignalComment;
import com.playground.repository.SignalCommentRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/signalComments")
public class SignalCommentController {

    @Autowired
    private SignalCommentRepository signalCommentRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<SignalComment>> getAllSignalComments() {
        ArrayList<SignalComment> listSignalComments = new ArrayList<>();
        for (SignalComment signalComment : signalCommentRepository.findAll()) {
            listSignalComments.add(signalComment);
        }
        return new ResponseEntity<>(listSignalComments,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SignalComment> getSignalCommentsById(@PathVariable(value = "id") int signalCommentId) throws ResourceNotFoundException {
        SignalComment signalComment = signalCommentRepository.findById(signalCommentId)
                .orElseThrow(() -> new ResourceNotFoundException("SignalComment with id " + signalCommentId + " not found"));
        return new ResponseEntity<>(signalComment, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public SignalComment createSignalComment(@Valid @RequestBody SignalComment signalComment) {
        return signalCommentRepository.save(signalComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SignalComment> updateSignalComment(@PathVariable(value = "id") int signalCommentId, @Valid @RequestBody SignalComment signalCommentDetails)
            throws ResourceNotFoundException {
        SignalComment signalComment = signalCommentRepository.findById(signalCommentId)
                .orElseThrow(() -> new ResourceNotFoundException("SignalComment with id " + signalCommentId + " not found"));
        signalComment.setDescription(signalCommentDetails.getDescription());
        final SignalComment updatedSignalComment = signalCommentRepository.save(signalComment);
        return ResponseEntity.ok(updatedSignalComment);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSignalComment(@PathVariable(value = "id") int signalCommentId) throws ResourceNotFoundException {
        SignalComment signalComment = signalCommentRepository.findById(signalCommentId)
                .orElseThrow(() -> new ResourceNotFoundException("SignalComment with id " + signalCommentId + " not found"));
        signalCommentRepository.delete(signalComment);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}