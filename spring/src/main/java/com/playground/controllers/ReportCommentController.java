package com.playground.controllers;

import com.playground.model.entity.Comment;
import com.playground.model.entity.ReportComment;
import com.playground.service.CommentService;
import com.playground.service.ReportCommentService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class ReportCommentController
 */
@RestController
public class ReportCommentController {

    /** ReportCommentService reportCommentService */
    private final ReportCommentService reportCommentService;

    /** CommentService commentService */
    private final CommentService commentService;

    /**
     * ReportCommentController constructor
     *
     * @param reportCommentService ReportCommentService
     * @param commentService CommentService
     */
    @Autowired
    public ReportCommentController(ReportCommentService reportCommentService, CommentService commentService) {
        this.reportCommentService = reportCommentService;
        this.commentService = commentService;
    }

    /**
     * [GET] Return all reports for comments
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/reportComments", produces = "application/json")
    public ResponseEntity<List<ReportComment>> getReportComments() {
        return new ResponseEntity<>(reportCommentService.getReportComments(), HttpStatus.OK);
    }

    /**
     * [GET] Return one report comment by id
     *
     * @param commentId int
     * @param reportCommentId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Comment not found
     * @throws ResourceNotFoundException ReportComment not found
     */
    @GetMapping(value = "/comments/{commentId}/reportComments/{reportCommentId}", produces = "application/json")
    public ResponseEntity<ReportComment> getReportComment(@PathVariable("commentId") int commentId, @PathVariable("reportCommentId") int reportCommentId) throws ResourceNotFoundException {
        Comment comment = commentService.getComment(commentId);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found");
        }

        ReportComment reportComment = reportCommentService.getReportCommentByComment(comment, reportCommentId);
        if (reportComment == null) throw new ResourceNotFoundException("ReportComment with id " + reportCommentId + " not found for this comment");

        return new ResponseEntity<>(reportComment, HttpStatus.OK);
    }

    /**
     * [POST] Create a report comment and return it
     *
     * @param commentId int
     * @param reportComment ReportComment
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Comment not found
     */
    @PostMapping(value = "/comments/{commentId}/reportComments", consumes = "application/json")
    public ResponseEntity<ReportComment> createReportComment(@PathVariable("commentId") int commentId, @RequestBody ReportComment reportComment) throws ResourceNotFoundException {
        Comment comment = commentService.getComment(commentId);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found");
        }

        return new ResponseEntity<>(reportCommentService.createReportComment(comment, reportComment), HttpStatus.CREATED);
    }

    /**
     * [PUT] Update a report comment and return it
     *
     * @param commentId int
     * @param reportCommentId int
     * @param reportComment ReportComment
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Comment not found
     * @throws ResourceNotFoundException ReportComment not found
     */
    @PutMapping("/comments/{commentId}/reportComments/{reportCommentId}")
    public ResponseEntity<ReportComment> updateReportComment(@PathVariable("commentId") int commentId, @PathVariable("reportCommentId") int reportCommentId, @RequestBody ReportComment reportComment) throws ResourceNotFoundException {

        Comment comment = commentService.getComment(commentId);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found");
        }

        ReportComment currentReportComment = reportCommentService.getReportCommentByComment(comment, reportCommentId);

        if (currentReportComment == null) {
            throw new ResourceNotFoundException("ReportComment with id " + reportCommentId + " not found for this comment");
        }

        return new ResponseEntity<>(reportCommentService.updateReportComment(reportCommentId, reportComment), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a report comment
     *
     * @param reportCommentId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Comment not found
     * @throws ResourceNotFoundException Comment not found
     */
    @DeleteMapping("/reportComments/{reportCommentId}")
    public ResponseEntity deleteReportComment(@PathVariable("reportCommentId") int reportCommentId) throws ResourceNotFoundException {
        ReportComment currentReportComment = reportCommentService.getReportComment(reportCommentId);

        if (currentReportComment == null) {
            throw new ResourceNotFoundException("ReportComment with id " + reportCommentId + " not found for this comment");
        }

        reportCommentService.deleteReportComment(currentReportComment);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}