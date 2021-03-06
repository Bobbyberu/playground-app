package com.playground.controllers;

import com.playground.model.dto.ReportCommentDto;
import com.playground.model.entity.Comment;
import com.playground.model.entity.ReportComment;
import com.playground.service.CommentService;
import com.playground.service.ReportCommentService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReportCommentController {

    @Inject
    private ReportCommentService reportCommentService;

    @Inject
    private CommentService commentService;

    /**
     * [GET] Return all reports for comments
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/reportComments", produces = "application/json")
    public ResponseEntity<List<ReportCommentDto>> getReportComments() {
        List<ReportCommentDto> commentsReported = reportCommentService.getReportComments().stream()
                .map(cr -> new ReportCommentDto(cr))
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsReported, HttpStatus.OK);
    }

    /**
     * [GET] Return one report comment by id
     *
     * @param reportCommentId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Comment not found
     * @throws ResourceNotFoundException ReportComment not found
     */
    @GetMapping(value = "/reportComments/{reportCommentId}", produces = "application/json")
    public ResponseEntity<ReportCommentDto> getReportComment(@PathVariable("reportCommentId") int reportCommentId) throws ResourceNotFoundException {

        ReportComment reportComment = reportCommentService.getReportComment(reportCommentId);
        if (reportComment == null) throw new ResourceNotFoundException("ReportComment with id " + reportCommentId + " not found for this comment");

        return new ResponseEntity<>(new ReportCommentDto(reportComment), HttpStatus.OK);
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
    public ResponseEntity<ReportCommentDto> createReportComment(@PathVariable("commentId") int commentId, @RequestBody ReportComment reportComment) throws ResourceNotFoundException {
        Comment comment = commentService.getComment(commentId);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found");
        }

        return new ResponseEntity<>(
                new ReportCommentDto(reportCommentService.createReportComment(comment, reportComment)),HttpStatus.CREATED);
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
    @PutMapping("/reportComments/{reportCommentId}")
    public ResponseEntity<ReportCommentDto> updateReportComment(@PathVariable("commentId") int commentId, @PathVariable("reportCommentId") int reportCommentId, @RequestBody ReportComment reportComment) throws ResourceNotFoundException {

        Comment comment = commentService.getComment(commentId);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found");
        }

        ReportComment currentReportComment = reportCommentService.getReportComment(reportCommentId);

        if (currentReportComment == null) {
            throw new ResourceNotFoundException("ReportComment with id " + reportCommentId + " not found for this comment");
        }

        return new ResponseEntity<>(
                new ReportCommentDto(reportCommentService.updateReportComment(reportCommentId, reportComment)), HttpStatus.OK);
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