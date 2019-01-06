package com.playground.controllers;

import com.playground.model.Playground;
import com.playground.repository.PlaygroundRepository;
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
@RequestMapping("/playgrounds")
public class PlaygroundController {

    @Autowired
    private PlaygroundRepository playgroundRepository;

    @GetMapping(value = "/", produces = "application/json")
    public List<Playground> getAllPlaygrounds() {
        return playgroundRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Playground> getPlaygroundsById(@PathVariable(value = "id") int playgroundId) throws ResourceNotFoundException {
        Playground playground = playgroundRepository.findById(playgroundId)
                .orElseThrow(() -> new ResourceNotFoundException("Playground with id " + playgroundId + " not found"));
        return new ResponseEntity<>(playground, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Playground createPlayground(@Valid @RequestBody Playground playground) {
        return playgroundRepository.save(playground);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playground> updatePlayground(@PathVariable(value = "id") int playgroundId, @Valid @RequestBody Playground playgroundDetails)
            throws ResourceNotFoundException {
        Playground playground = playgroundRepository.findById(playgroundId)
                .orElseThrow(() -> new ResourceNotFoundException("Playground with id " + playgroundId + " not found"));
        playground.setName(playgroundDetails.getName());
        playground.setSurface(playgroundDetails.getSurface());
        playground.setSports(playgroundDetails.getSports());
        playground.setPrivate(playgroundDetails.isPrivate());
        playground.setPlayers(playgroundDetails.getPlayers());
        playground.setLongitude(playgroundDetails.getLongitude());
        playground.setLatitude(playgroundDetails.getLatitude());
        playgroundDetails.setImage(playgroundDetails.getImage());
        playground.setDescription(playgroundDetails.getDescription());
        playground.setCovered(playgroundDetails.isCovered());
        playground.setAverageMark(playgroundDetails.getAverageMark());
        final Playground updatedPlayground = playgroundRepository.save(playground);
        return ResponseEntity.ok(updatedPlayground);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deletePlayground(@PathVariable(value = "id") int playgroundId) throws ResourceNotFoundException {
        Playground playground = playgroundRepository.findById(playgroundId)
                .orElseThrow(() -> new ResourceNotFoundException("Playground with id " + playgroundId + " not found"));
        playgroundRepository.delete(playground);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}