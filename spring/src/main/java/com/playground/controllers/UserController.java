package com.playground.controllers;

import com.playground.model.User;
import com.playground.repository.UserRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/", produces = "application/json")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        user.setUsername(userDetails.getUsername());
        user.setCity(userDetails.getCity());
        user.setMail(userDetails.getMail());
        user.setRole(userDetails.getRole());
        user.setPassword(userDetails.getPassword());
        user.setFriends(userDetails.getFriends());
        user.setFavouriteSports(userDetails.getFavouriteSports());
        user.setBirthDate(userDetails.getBirthDate());
        user.setFavouritePlaygrounds(userDetails.getFavouritePlaygrounds());
        user.setAvatar(userDetails.getAvatar());
        user.setEnabled(userDetails.isEnabled());
        user.setArchived(userDetails.isArchived());
        user.setBanned(userDetails.isBanned());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
