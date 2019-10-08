package com.playground.service.interfaces;

import com.playground.model.entity.Playground;
import com.playground.model.entity.Schedule;
import com.playground.model.wrapper.ScheduleWrapper;

import java.util.List;

/**
 * Interface IScheduleService
 */
public interface IScheduleService {

    /**
     * Return one schedules
     *
     * @param id int
     *
     * @return Schedule
     */
    Schedule getSchedule(int id);

    /**
     * Return all schedules for given playground
     *
     * @param playground Playground
     *
     * @return List<Schedule>
     */
    List<Schedule> getPlaygroundSchedule(Playground playground);

    /**
     * Create schedule and return it
     *
     * @param schedule Schedule
     *
     * @return Schedule
     */
    Schedule createSchedule(Schedule schedule);

    /**
     * Update a schedule and return it
     *
     * @param id int
     * @param schedule Schedule
     *
     * @return Schedule
     */
    Schedule updateSchedule(int id, Schedule schedule);

    /**
     * Delete a schedule
     *
     * @param schedule Schedule
     */
    void deleteSchedule(Schedule schedule);

    /**
     * @param scheduleWrapper
     * @return true if one of the schedules has invalid data
     */
    boolean isTimeInvalid(ScheduleWrapper scheduleWrapper);
}
