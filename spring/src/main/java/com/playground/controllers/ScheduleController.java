package com.playground.controllers;

import com.playground.model.Schedule;
import com.playground.repository.ScheduleRepository;
import com.playground.service.ScheduleService;
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

/**
 * Class ScheduleController
 */
@RestController
@RequestMapping("/hours")
public class ScheduleController {

    /** ScheduleService scheduleService */
    private final ScheduleService scheduleService;

    /**
     * ScheduleController Constructor
     *
     * @param scheduleService ScheduleService
     */
    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * [GET] Return all schedules
     *
     * @return ResponseEntity
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Schedule>> getSchedules() {
        return new ResponseEntity<>(scheduleService.getSchedules(), HttpStatus.OK);
    }

    /**
     * [GET] Return one schedule by id
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Schedule not found
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Schedule> getSchedule(@PathVariable("id") int id) throws ResourceNotFoundException {
        Schedule schedule = scheduleService.getSchedule(id);

        if (schedule == null) {
            throw new ResourceNotFoundException("Schedule with id " + id + " not found");
        }

        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    /**
     * [POST] Create a schedule and return it
     *
     * @param schedule Schedule
     *
     * @return ResponseEntity
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return new ResponseEntity<>(scheduleService.createSchedule(schedule), HttpStatus.CREATED);
    }

    /**
     * [PUT] Update a schedule and return it
     *
     * @param id int
     * @param schedule Schedule
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Schedule not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable("id") int id, @RequestBody Schedule schedule) throws ResourceNotFoundException {
        Schedule currentSchedule = scheduleService.getSchedule(id);

        if (currentSchedule == null) {
            throw new ResourceNotFoundException("Schedule with id " + id + " not found");
        }

        return new ResponseEntity<>(scheduleService.updateSchedule(id, schedule), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a schedule
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Schedule not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable("id") int id) throws ResourceNotFoundException {
        Schedule currentSchedule = scheduleService.getSchedule(id);

        if (currentSchedule == null) {
            throw new ResourceNotFoundException("Schedule with id " + id + " not found");
        }

        scheduleService.deleteSchedule(currentSchedule);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
