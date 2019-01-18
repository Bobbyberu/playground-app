package com.playground.controllers;

import com.playground.model.Comment;
import com.playground.model.Playground;
import com.playground.service.CommentService;
import com.playground.service.PlaygroundService;
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
public class CommentController {

    /** CommentService commentService */
    private final CommentService commentService;

    /** PlaygroundService playgroundService */
    private final PlaygroundService playgroundService;

    /**
     * CommentController Constructor
     *
     * @param commentService CommentService
     * @param playgroundService PlaygroundService
     */
    @Autowired
    public CommentController(CommentService commentService, PlaygroundService playgroundService) {
        this.commentService = commentService;
        this.playgroundService = playgroundService;
    }

    /**
     * [GET] Return all comments
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/comments", produces = "application/json")
    public ResponseEntity<List<Comment>> getComments() {
        return new ResponseEntity<>(commentService.getComments(), HttpStatus.OK);
    }

    /**
     * [GET] Return one comment by id
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Comment not found
     */
    @GetMapping(value = "/comments/{id}", produces = "application/json")
    public ResponseEntity<?> getComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        Comment comment = commentService.getComment(id);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    /**
     * [POST] Create a comment and return it
     *
     * @param comment Comment
     *
     * @return ResponseEntity
     */
    @PostMapping(value = "/comments", consumes = "application/json")
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
     *
     * @throws ResourceNotFoundException Comment not found
     */
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") int id, @RequestBody Comment comment) throws ResourceNotFoundException {
        Comment currentComment = commentService.getComment(id);

        if (currentComment == null) {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }

        return new ResponseEntity<>(commentService.updateComment(id, comment), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a comment
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Comment not found
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        Comment currentComment = commentService.getComment(id);

        if (currentComment == null) {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
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
     *
     * @throws ResourceNotFoundException Comment not found
     */
    @PutMapping("/comments/archived/{id}")
    public ResponseEntity<Comment> archivedComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        Comment currentComment = commentService.getComment(id);

        if (currentComment == null) {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }

        return new ResponseEntity<>(commentService.archivedComment(currentComment), HttpStatus.OK);
    }

    /**
     * [GET] Return all comments of a playground
     *
     * @param playgroundId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @GetMapping(value = "/playgrounds/{playgroundId}/comments", produces = "application/json")
    public ResponseEntity<List<Comment>> search(@PathVariable(value = "playgroundId") int playgroundId) throws ResourceNotFoundException {
        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        List<Comment> listComments = commentService.getCommentByPlaygroundId(playground);

        return new ResponseEntity<>(listComments, HttpStatus.OK);
    }
}
