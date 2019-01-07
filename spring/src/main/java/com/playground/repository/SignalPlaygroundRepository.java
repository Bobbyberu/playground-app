package com.playground.repository;

import com.playground.model.SignalPlayground;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignalPlaygroundRepository extends CrudRepository<SignalPlayground, Integer> {

}