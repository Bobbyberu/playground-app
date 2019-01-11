package com.playground.controllers;

import com.playground.model.Comment;
import com.playground.service.CommentService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class CommentController
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    /** CommentService commentService */
    private final CommentService commentService;

    /**
     * CommentController Constructor
     *
     * @param commentService CommentService
     */
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * [GET] Return all comments
     *
     * @return ResponseEntity<List<Comment>>
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Comment>> getComments() {
        return new ResponseEntity<>(commentService.getComments(), HttpStatus.OK);
    }

    /**
     * [GET] Return one comment by id
     *
     * @param id int
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getComment(@PathVariable("id") int id) {
        Comment comment = commentService.getComment(id);

        if (comment == null) {
            return new ResponseEntity<>(new ResourceNotFoundException("Comment with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    /**
     * [POST] Create a comment and return it
     *
     * @param comment Comment
     *
     * @return ResponseEntity<Comment>
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.createComment(comment), HttpStatus.CREATED);
    }

    /**
     * [PUT] Update a comment and return it
     *
     * @param id int
     * @param comment Comment
     *
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") int id, @RequestBody Comment comment) throws ResourceNotFoundException {
        Comment currentComment = commentService.getComment(id);

        if (currentComment == null) {
            return new ResponseEntity<>(new ResourceNotFoundException("Comment with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commentService.updateComment(id, comment), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a comment
     *
     * @param id int
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") int id) {
        Comment currentComment = commentService.getComment(id);

        if (currentComment == null) {
            return new ResponseEntity<>(new ResourceNotFoundException("Comment with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        commentService.deleteComment(currentComment);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * [PUT] Archived a comment
     *
     * @param id int
     *
     * @return ResponseEntity
     */
    @PutMapping("/archived/{id}")
    public ResponseEntity<?> archivedComment(@PathVariable("id") int id) {
        Comment currentComment = commentService.getComment(id);

        if (currentComment == null) {
            return new ResponseEntity<>(new ResourceNotFoundException("Comment with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commentService.archivedComment(currentComment), HttpStatus.OK);
    }
}
