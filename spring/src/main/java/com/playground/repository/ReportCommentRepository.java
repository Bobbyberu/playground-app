package com.playground.repository;

import com.playground.model.entity.Comment;
import com.playground.model.entity.ReportComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportCommentRepository extends CrudRepository<ReportComment, Integer> {

    @Query("SELECT rc FROM ReportComment rc WHERE rc.comment= ?1")
    List<ReportComment> getByComment(Comment comment);

    @Query("SELECT rc FROM ReportComment rc WHERE rc.comment= ?1 AND rc.id = ?2")
    ReportComment getOneByComment(Comment comment, int reportCommentId);
}