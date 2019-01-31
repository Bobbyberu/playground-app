package com.playground.repository;

import com.playground.model.entity.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer> {

}