package com.playground.controllers;

import com.playground.model.dto.SportDto;
import com.playground.model.entity.Sport;
import com.playground.service.SportService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sports")
public class SportController {

    @Inject
    private SportService sportService;

    /**
     * [GET] Return all sports
     *
     * @return ResponseEntity
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SportDto>> getSports() {
        List<SportDto> sports = sportService.getSports().stream()
                .map(s -> new SportDto(s))
                .collect(Collectors.toList());
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }

    /**
     * [GET] Return one sport by id
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Sport not found
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SportDto> getSport(@PathVariable("id") int id) throws ResourceNotFoundException {
        Sport sport = sportService.getSport(id);

        if (sport == null) {
            throw new ResourceNotFoundException("Sport with id " + id + " not found");
        }

        return new ResponseEntity<>(new SportDto(sport), HttpStatus.OK);
    }

    /**
     * [POST] Create a sport and return it
     *
     * @param sport Sport
     *
     * @return ResponseEntity
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<SportDto> createSport(@RequestBody Sport sport) {
        return new ResponseEntity<>(new SportDto(sportService.createSport(sport)), HttpStatus.CREATED);
    }

    /**
     * [PUT] Update a sport and return it
     *
     * @param id int
     * @param sport Sport
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Sport not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<SportDto> updateSport(@PathVariable("id") int id, @RequestBody Sport sport) throws ResourceNotFoundException {
        Sport currentSport = sportService.getSport(id);

        if (currentSport == null) {
            throw new ResourceNotFoundException("Sport with id " + id + " not found");
        }

        return new ResponseEntity<>(new SportDto(sportService.updateSport(id, sport)), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a sport
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Sport not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSport(@PathVariable("id") int id) throws ResourceNotFoundException {
        Sport currentSport = sportService.getSport(id);

        if (currentSport == null) {
            throw new ResourceNotFoundException("Sport with id " + id + " not found");
        }

        sportService.deleteSport(currentSport);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
