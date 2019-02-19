package com.playground.service;

import com.playground.model.entity.Playground;
import com.playground.model.entity.Schedule;
import com.playground.repository.ScheduleRepository;
import com.playground.service.interfaces.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
    public Schedule getSchedule(int id) {
        return scheduleRepository.findById(id).orElse(null);
}

    @Override
    public List<Schedule> getPlaygroundSchedule(Playground playground) {
        List<Schedule> schedules = scheduleRepository.findByPlayground(playground);
        schedules.sort((o1, o2) -> {
            if (o1.getDay().getValue() == o2.getDay().getValue()) return 0;
            return o1.getDay().compareTo(o2.getDay());
        });
        return schedules;
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
