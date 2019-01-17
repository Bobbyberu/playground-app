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
 * Class PlaygroundController
 */
@RestController
@RequestMapping("/playgrounds")
public class PlaygroundController {

    /** PlaygroundService playgroundService */
    private final PlaygroundService playgroundService;

    /**
     * PlaygroundController Constructor
     *
     * @param playgroundService PlaygroundService
     */
    @Autowired
    public PlaygroundController(PlaygroundService playgroundService) {
        this.playgroundService = playgroundService;
    }

    @Autowired
    CommentService commentService;

    /**
     * [GET] Return all playgrounds
     *
     * @return ResponseEntity<List<Playground>>
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Playground>> getPlaygrounds() {
        return new ResponseEntity<>(playgroundService.getPlaygrounds(),HttpStatus.OK);
    }

    /**
     * [GET] Return one playground by id
     *
     * @param id int
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getPlaygroundsById(@PathVariable("id") int id) {
        Playground playground = playgroundService.getPlayground(id);

        if (playground == null) {
            return new ResponseEntity<>(new ResourceNotFoundException("Playground with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(playground, HttpStatus.OK);
    }

    /**
     * [POST] Create a playground and return it
     *
     * @param playground Playground
     *
     * @return ResponseEntity<Playground>
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Playground> createPlayground(@RequestBody Playground playground) {
        return new ResponseEntity<>(playgroundService.createPlayground(playground), HttpStatus.CREATED);
    }

    /**
     * [PUT] Update a playground and return it
     *
     * @param id int
     * @param playground Playground
     *
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayground(@PathVariable(value = "id") int id, @RequestBody Playground playground) {
        Playground currentPlayground = playgroundService.getPlayground(id);

        if (currentPlayground == null) {
            return new ResponseEntity<>(new ResourceNotFoundException("Playground with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(playgroundService.updatePlayground(id, playground), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a playground
     *
     * @param id int
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayground(@PathVariable("id") int id) {
        Playground currentPlayground = playgroundService.getPlayground(id);

        if (currentPlayground == null) {
            return new ResponseEntity<>(new ResourceNotFoundException("Playground with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        playgroundService.deletePlayground(currentPlayground);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * [GET] Return all playgrounds which are linked with the keyword (sport, name ...)
     *
     * @param keyword String
     *
     * @return ResponseEntity<List<Playground>>
     */
    @GetMapping(value = "/search/{keyword}", produces = "application/json")
    public ResponseEntity<List<Playground>> search(@PathVariable("keyword") String keyword) {
        return new ResponseEntity<>(playgroundService.searchPlaygroundByKeyword(keyword), HttpStatus.OK);
    }

    /**
     * TODO:: Move this function in Comments Controller (@Dylan)
     * [GET] Return all comments of a playground
     *
     *
     * @param id int
     *
     * @return ResponseEntity<List<Comment>>
     */
    @GetMapping(value = "/{id}/comments", produces = "application/json")
    public ResponseEntity<List<Comment>> search(@PathVariable(value = "id") int id) {
        Playground playground = playgroundService.getPlayground(id);

        List<Comment> listComments = commentService.getCommentByPlaygroundId(playground);

        return new ResponseEntity<>(listComments, HttpStatus.OK);
    }
}
