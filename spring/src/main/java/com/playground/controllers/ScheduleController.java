package com.playground.controllers;

import com.playground.model.entity.Schedule;
import com.playground.service.ScheduleService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Inject
    private ScheduleService scheduleService;

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
