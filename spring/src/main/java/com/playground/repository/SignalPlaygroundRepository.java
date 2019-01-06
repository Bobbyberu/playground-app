package com.playground.repository;

import com.playground.model.SignalPlayground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignalPlaygroundRepository extends JpaRepository<SignalPlayground, Integer> {

}