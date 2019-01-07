package com.playground.repository;

import com.playground.model.Playground;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaygroundRepository extends CrudRepository<Playground, Integer> {

}
