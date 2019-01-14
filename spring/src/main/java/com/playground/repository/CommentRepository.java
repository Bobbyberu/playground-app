package com.playground.repository;

import com.playground.model.Comment;
import com.playground.model.Playground;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.playground = ?1")
    List<Comment> getByPlaygroundId(Playground playground);
}