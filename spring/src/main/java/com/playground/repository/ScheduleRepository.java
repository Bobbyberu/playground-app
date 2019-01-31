package com.playground.repository;

import com.playground.model.entity.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {

}