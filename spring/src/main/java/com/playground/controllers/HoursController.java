package com.playground.controllers;

import com.playground.model.Hours;
import com.playground.repository.HoursRepository;
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
@RequestMapping("/hours")
public class HoursController {

    @Autowired
    private HoursRepository hoursRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Hours>> getAllHours() {
        ArrayList<Hours> listHours = new ArrayList<>();
        for (Hours hours : hoursRepository.findAll()) {
            listHours.add(hours);
        }
        return new ResponseEntity<>(listHours,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Hours> getHoursById(@PathVariable(value = "id") int hoursId) throws ResourceNotFoundException {
        Hours hours = hoursRepository.findById(hoursId)
                .orElseThrow(() -> new ResourceNotFoundException("Hours with id " + hoursId + " not found"));
        return new ResponseEntity<>(hours, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Hours createHours(@Valid @RequestBody Hours hours) {
        return hoursRepository.save(hours);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hours> updateHours(@PathVariable(value = "id") int hoursId, @Valid @RequestBody Hours hoursDetails)
            throws ResourceNotFoundException {
        Hours hours = hoursRepository.findById(hoursId)
                .orElseThrow(() -> new ResourceNotFoundException("Hours with id " + hoursId + " not found"));
        hours.setDay(hoursDetails.getDay());
        hours.setOpening(hoursDetails.getOpening());
        hours.setClose(hoursDetails.getClose());
        final Hours updatedHours = hoursRepository.save(hours);
        return ResponseEntity.ok(updatedHours);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteHours(@PathVariable(value = "id") int hoursId) throws ResourceNotFoundException {
        Hours hours = hoursRepository.findById(hoursId)
                .orElseThrow(() -> new ResourceNotFoundException("Hours with id " + hoursId + " not found"));
        hoursRepository.delete(hours);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}