package com.playground.repository;

import com.playground.model.entity.Sport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends CrudRepository<Sport, Integer> {

}