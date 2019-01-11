package com.playground.service;

import com.playground.model.Schedule;
import com.playground.repository.ScheduleRepository;
import com.playground.service.interfaces.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ScheduleService
 */
@Service
public class ScheduleService implements IScheduleService {

    /** ScheduleRepository scheduleRepository */
    private final ScheduleRepository scheduleRepository;

    /**
     * ScheduleService Constructor
     *
     * @param scheduleRepository ScheduleRepository
     */
    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        scheduleRepository.findAll().forEach(schedules::add);

        return schedules;
    }

    @Override
    public Schedule getSchedule(int id) {
        return scheduleRepository.findById(id).orElse(null);
}

    @Override
    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule updateSchedule(int id, Schedule schedule) {
        schedule.setId(id);
        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }
}
