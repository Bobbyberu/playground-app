package com.playground.controllers;

import com.playground.model.entity.Sport;
import com.playground.service.SportService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class SportController
 */
@RestController
@RequestMapping("/sports")
public class SportController {

    /** SportService sportService */
    private final SportService sportService;

    /**
     * SportController Constructor
     *
     * @param sportService SportService
     */
    @Autowired
    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    /**
     * [GET] Return all sports
     *
     * @return ResponseEntity
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Sport>> getSports() {
        return new ResponseEntity<>(sportService.getSports(), HttpStatus.OK);
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
    public ResponseEntity<Sport> getSport(@PathVariable("id") int id) throws ResourceNotFoundException {
        Sport sport = sportService.getSport(id);

        if (sport == null) {
            throw new ResourceNotFoundException("Sport with id " + id + " not found");
        }

        return new ResponseEntity<>(sport, HttpStatus.OK);
    }

    /**
     * [POST] Create a sport and return it
     *
     * @param sport Sport
     *
     * @return ResponseEntity
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Sport> createSport(@RequestBody Sport sport) {
        return new ResponseEntity<>(sportService.createSport(sport), HttpStatus.CREATED);
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
    public ResponseEntity<Sport> updateSport(@PathVariable("id") int id, @RequestBody Sport sport) throws ResourceNotFoundException {
        Sport currentSport = sportService.getSport(id);

        if (currentSport == null) {
            throw new ResourceNotFoundException("Sport with id " + id + " not found");
        }

        return new ResponseEntity<>(sportService.updateSport(id, sport), HttpStatus.OK);
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
