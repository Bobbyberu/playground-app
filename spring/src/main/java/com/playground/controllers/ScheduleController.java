package com.playground.controllers;

import com.playground.model.entity.Schedule;
import com.playground.service.ScheduleService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class ScheduleController
 */
@RestController
@RequestMapping("/schedules")
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
