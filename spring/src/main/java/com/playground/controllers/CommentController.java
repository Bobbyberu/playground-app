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
     * @param playgroundId int
     * @param commentId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     * @throws ResourceNotFoundException Comment not found
     */
    @GetMapping(value = "/playgrounds/{playgroundId}/comments/{commentId}", produces = "application/json")
    public ResponseEntity<Comment> getComment(@PathVariable("playgroundId") int playgroundId, @PathVariable("commentId") int commentId) throws ResourceNotFoundException {

        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        Comment comment = commentService.getCommentByPlayground(playground, commentId);
        if (comment == null) throw new ResourceNotFoundException("Comment with id " + commentId + " not found for this playground");

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    /**
     * [POST] Create a comment and return it
     *
     * @param playgroundId int
     * @param comment Comment
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @PostMapping(value = "/playgrounds/{playgroundId}/comments", consumes = "application/json")
    public ResponseEntity<Comment> createComment(@PathVariable("playgroundId") int playgroundId, @RequestBody Comment comment) throws ResourceNotFoundException {
        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        return new ResponseEntity<>(commentService.createComment(playground, comment), HttpStatus.CREATED);
    }

    /**
     * [PUT] Update a comment and return it
     *
     * @param playgroundId int
     * @param commentId int
     * @param comment Comment
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     * @throws ResourceNotFoundException Comment not found
     */
    @PutMapping("/playgrounds/{playgroundId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable("playgroundId") int playgroundId, @PathVariable("commentId") int commentId, @RequestBody Comment comment) throws ResourceNotFoundException {

        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        Comment currentComment = commentService.getCommentByPlayground(playground, commentId);

        if (currentComment == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found for this playground");
        }

        return new ResponseEntity<>(commentService.updateComment(commentId, comment), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a comment
     *
     * @param commentId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     * @throws ResourceNotFoundException Comment not found
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") int commentId) throws ResourceNotFoundException {

        Comment currentComment = commentService.getComment(commentId);

        if (currentComment == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found for this playground");
        }

        commentService.deleteComment(currentComment);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * [PUT] Archived a comment
     *
     * @param playgroundId playgroundId
     * @param commentId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Comment not found
     */
    @PutMapping("/playgrounds/{playgroundId}/comments/archived/{commentId}")
    public ResponseEntity<Comment> archivedComment(@PathVariable("playgroundId") int playgroundId, @PathVariable("commentId") int commentId) throws ResourceNotFoundException {
        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        Comment currentComment = commentService.getCommentByPlayground(playground, commentId);

        if (currentComment == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found for this playground");
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
    public ResponseEntity<List<Comment>> getCommentsByPlaygroundId(@PathVariable(value = "playgroundId") int playgroundId) throws ResourceNotFoundException {
        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        List<Comment> listComments = commentService.getCommentsByPlayground(playground);

        return new ResponseEntity<>(listComments, HttpStatus.OK);
    }
}
