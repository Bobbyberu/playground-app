package com.playground.controllers;

import com.playground.model.Comment;
import com.playground.service.CommentService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Return all comments
     *
     * @return List<Comment>
     */
    @GetMapping(produces = "application/json")
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    /**
     * Return one comment by id
     *
     * @param id int
     * @return Comment
     * @throws ResourceNotFoundException No Comment Found
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public Comment getComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        return commentService.getComment(id);
    }

    /**
     * Create a comment and return it
     *
     * @param comment Comment
     * @return Comment
     */
    @PostMapping(consumes = "application/json")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    /**
     * Update a comment and return it
     *
     * @param id int
     * @param comment Comment
     * @return Comment
     * @throws ResourceNotFoundException No Comment Found
     */
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable("id") int id, @RequestBody Comment comment) throws ResourceNotFoundException {
        return commentService.updateComment(id, comment);
    }

    /**
     * Delete a comment
     *
     * @param id int
     * @throws ResourceNotFoundException No Comment Found
     */
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        commentService.deleteComment(id);
    }

    /**
     * Archived a comment
     *
     * @param id int
     * @return Comment
     * @throws ResourceNotFoundException No Comment Found
     */
    @GetMapping("/{id}/archived")
    public Comment archivedComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        return commentService.archivedComment(id);
    }
}
