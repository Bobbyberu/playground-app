package com.playground.repository;

import com.playground.model.Hours;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoursRepository extends CrudRepository<Hours, Integer> {

}