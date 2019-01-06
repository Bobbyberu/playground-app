package com.playground.controllers;

import com.playground.model.SignalPlayground;
import com.playground.repository.SignalPlaygroundRepository;
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
@RequestMapping("/signalPlaygrounds")
public class SignalPlaygroundController {

    @Autowired
    private SignalPlaygroundRepository signalPlaygroundRepository;

    @GetMapping(value = "/", produces = "application/json")
    public List<SignalPlayground> getAllSignalPlaygrounds() {
        return signalPlaygroundRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SignalPlayground> getSignalPlaygroundsById(@PathVariable(value = "id") int signalPlaygroundId) throws ResourceNotFoundException {
        SignalPlayground signalPlayground = signalPlaygroundRepository.findById(signalPlaygroundId)
                .orElseThrow(() -> new ResourceNotFoundException("SignalPlayground with id " + signalPlaygroundId + " not found"));
        return new ResponseEntity<>(signalPlayground, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public SignalPlayground createSignalPlayground(@Valid @RequestBody SignalPlayground signalPlayground) {
        return signalPlaygroundRepository.save(signalPlayground);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SignalPlayground> updateSignalPlayground(@PathVariable(value = "id") int signalPlaygroundId, @Valid @RequestBody SignalPlayground signalPlaygroundDetails)
            throws ResourceNotFoundException {
        SignalPlayground signalPlayground = signalPlaygroundRepository.findById(signalPlaygroundId)
                .orElseThrow(() -> new ResourceNotFoundException("SignalPlayground with id " + signalPlaygroundId + " not found"));
        signalPlayground.setDescription(signalPlaygroundDetails.getDescription());
        final SignalPlayground updatedSignalPlayground = signalPlaygroundRepository.save(signalPlayground);
        return ResponseEntity.ok(updatedSignalPlayground);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSignalPlayground(@PathVariable(value = "id") int signalPlaygroundId) throws ResourceNotFoundException {
        SignalPlayground signalPlayground = signalPlaygroundRepository.findById(signalPlaygroundId)
                .orElseThrow(() -> new ResourceNotFoundException("SignalPlayground with id " + signalPlaygroundId + " not found"));
        signalPlaygroundRepository.delete(signalPlayground);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}