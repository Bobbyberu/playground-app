package com.playground.controllers;

import com.playground.model.Playground;
import com.playground.model.User;
import com.playground.repository.PlaygroundRepository;
import com.playground.repository.UserRepository;
import com.playground.service.PlaygroundService;
import com.playground.service.UserService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class PlaygroundController
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /** UserService userService */
    private final UserService userService;

    /** PlaygroundService playgroundService */
    private final PlaygroundService playgroundService;

    /**
     * UserController Constructor
     *
     * @param userService UserService
     * @param playgroundService PlaygroundService
     */
    @Autowired
    public UserController(UserService userService, PlaygroundService playgroundService) {
        this.userService = userService;
        this.playgroundService = playgroundService;
    }

    /**
     * [GET] Return all users
     *
     * @return ResponseEntity
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }


    /**
     * [GET] return true if the user doing the request is authenticated
     *
     * @return Boolean
     */
    @GetMapping(value = "/check")
    public ResponseEntity<Boolean> checkAuth() {
        return ResponseEntity.ok(true);
    }

    /**
     * [GET] Return an user by his username
     *
     * @param username String
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException User not found
     */
    @GetMapping(value = "/username/{username}", produces = "application/json")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) throws ResourceNotFoundException {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     * [GET] Return a distinct list of playgrounds
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException User not found
     */
    @GetMapping(value = "/{id}/favouritePlaygrounds/")
    public ResponseEntity<Set<Playground>> getFavouritePlaygrounds(@PathVariable("id") int id) throws ResourceNotFoundException {
        User user = userService.getUser(id);

        if (user == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        return new ResponseEntity<>(user.getFavouritePlaygrounds(), HttpStatus.OK);
    }

    /**
     * [GET] Return true if it's a user's favorite playground
     *
     * @param userId int
     * @param playgroundId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException User not found
     * @throws ResourceNotFoundException Playground not found
     */
    @GetMapping(value = "/{userId}/favouritePlaygrounds/{playgroundId}")
    public ResponseEntity<Boolean> getIfPlaygroundIsFavourite(@PathVariable("userId") int userId, @PathVariable("playgroundId") int playgroundId) throws ResourceNotFoundException {
        User user = userService.getUser(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }

        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        Boolean favourite = user.getFavouritePlaygrounds().contains(playground);

        return new ResponseEntity<>(favourite, HttpStatus.OK);
    }

    /**
     * [GET] Return an user by his id
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException User not found
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) throws ResourceNotFoundException {
        User user = userService.getUser(id);

        if (user == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * [POST] Create a user and return it
     *
     * @param user User
     *
     * @return ResponseEntity
     */
    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    /**
     * [PUT] add/remove a user's favourite playground
     *
     * @param userId int
     * @param playgroundId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException User not found
     * @throws ResourceNotFoundException Playground not found
     */
    @PutMapping("/{userId}/favouritePlaygrounds/{playgroundId}")
    public ResponseEntity<Boolean> togglePlaygroundFavorite(@PathVariable("userId") int userId, @PathVariable("playgroundId") int playgroundId) throws ResourceNotFoundException {
        User user = userService.getUser(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }

        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        boolean favourite;

        if(user.getFavouritePlaygrounds().contains(playground)) {
            user.getFavouritePlaygrounds().remove(playground);
            favourite = false;
        } else {
            user.getFavouritePlaygrounds().add(playground);
            favourite = true;
        }

        userService.updateUser(userId, user);

        return new ResponseEntity<>(favourite, HttpStatus.OK);
    }

    /**
     * [PUT] Update an user and return it
     *
     * @param id int
     * @param user User
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException User not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) throws ResourceNotFoundException {
        User currentUser = userService.getUser(id);

        if (currentUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete an user
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException User not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) throws ResourceNotFoundException {
        User currentUser = userService.getUser(id);

        if (currentUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        userService.deleteUser(currentUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
