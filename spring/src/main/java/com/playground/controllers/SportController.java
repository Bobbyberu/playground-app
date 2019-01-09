package com.playground.controllers;

import com.playground.model.Sport;
import com.playground.repository.SportRepository;
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
@RequestMapping("/sports")
public class SportController {

    @Autowired
    private SportRepository sportRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Sport>> getAllSports() {
        ArrayList<Sport> listSports = new ArrayList<>();
        for (Sport sport : sportRepository.findAll()) {
            listSports.add(sport);
        }
        return new ResponseEntity<>(listSports,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Sport> getSportsById(@PathVariable(value = "id") int sportId) throws ResourceNotFoundException {
        Sport sport = sportRepository.findById(sportId)
                .orElseThrow(() -> new ResourceNotFoundException("Sport with id " + sportId + " not found"));
        return new ResponseEntity<>(sport, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Sport createSport(@Valid @RequestBody Sport sport) {
        return sportRepository.save(sport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sport> updateSport(@PathVariable(value = "id") int sportId, @Valid @RequestBody Sport sportDetails)
            throws ResourceNotFoundException {
        Sport sport = sportRepository.findById(sportId)
                .orElseThrow(() -> new ResourceNotFoundException("Sport with id " + sportId + " not found"));
        sport.setName(sportDetails.getName());
        sport.setSymbol(sportDetails.getSymbol());
        final Sport updatedSport = sportRepository.save(sport);
        return ResponseEntity.ok(updatedSport);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSport(@PathVariable(value = "id") int sportId) throws ResourceNotFoundException {
        Sport sport = sportRepository.findById(sportId)
                .orElseThrow(() -> new ResourceNotFoundException("Sport with id " + sportId + " not found"));
        sportRepository.delete(sport);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
