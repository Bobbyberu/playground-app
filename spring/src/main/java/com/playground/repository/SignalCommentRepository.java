package com.playground.repository;

import com.playground.model.SignalComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignalCommentRepository extends CrudRepository<SignalComment, Integer> {

}