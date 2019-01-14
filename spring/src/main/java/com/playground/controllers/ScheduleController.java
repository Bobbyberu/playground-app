package com.playground.controllers;

import com.playground.model.Schedule;
import com.playground.repository.ScheduleRepository;
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
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Schedule>> getAllHours() {
        ArrayList<Schedule> listHours = new ArrayList<>();
        for (Schedule schedule : scheduleRepository.findAll()) {
            listHours.add(schedule);
        }
        return new ResponseEntity<>(listHours,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Schedule> getHoursById(@PathVariable(value = "id") int hoursId) throws ResourceNotFoundException {
        Schedule schedule = scheduleRepository.findById(hoursId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + hoursId + " not found"));
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Schedule createHours(@Valid @RequestBody Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateHours(@PathVariable(value = "id") int hoursId, @Valid @RequestBody Schedule scheduleDetails)
            throws ResourceNotFoundException {
        Schedule schedule = scheduleRepository.findById(hoursId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + hoursId + " not found"));
        schedule.setDay(scheduleDetails.getDay());
        schedule.setOpening(scheduleDetails.getOpening());
        schedule.setClose(scheduleDetails.getClose());
        final Schedule updatedSchedule = scheduleRepository.save(schedule);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteHours(@PathVariable(value = "id") int hoursId) throws ResourceNotFoundException {
        Schedule schedule = scheduleRepository.findById(hoursId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + hoursId + " not found"));
        scheduleRepository.delete(schedule);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
