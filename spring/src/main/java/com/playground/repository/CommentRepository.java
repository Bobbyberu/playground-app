package com.playground.repository;

import com.playground.model.entity.Comment;
import com.playground.model.entity.Playground;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    List<Comment> getCommentsByPlayground(Playground playground);
}