package com.playground.service.interfaces;

import com.playground.model.Schedule;

import java.util.List;

/**
 * Interface IScheduleService
 */
public interface IScheduleService {

    /**
     * Return all schedules
     *
     * @return List<Schedule>
     */
    List<Schedule> getSchedules();

    /**
     * Return one schedules
     *
     * @param id int
     *
     * @return Schedule
     */
    Schedule getSchedule(int id);

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
}
