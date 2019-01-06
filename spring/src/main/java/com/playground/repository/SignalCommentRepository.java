package com.playground.repository;

import com.playground.model.SignalComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignalCommentRepository extends JpaRepository<SignalComment, Long> {

}