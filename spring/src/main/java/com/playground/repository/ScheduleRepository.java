package com.playground.repository;

import com.playground.model.entity.Playground;
import com.playground.model.entity.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {

    List<Schedule> findByPlayground(Playground playground);
}