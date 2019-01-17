package com.playground.controllers;

import com.playground.model.Playground;
import com.playground.model.User;
import com.playground.repository.PlaygroundRepository;
import com.playground.repository.UserRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaygroundRepository playgroundRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {
        ArrayList<User> listUsers = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            listUsers.add(user);
        }
        return new ResponseEntity<>(listUsers,HttpStatus.OK);
    }

    @GetMapping("/{id}/favouritePlaygrounds/")
    public ResponseEntity<Set<Playground>> getFavouritePlaygrounds(@PathVariable(value = "id") int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        return ResponseEntity.ok(user.getFavouritePlaygrounds());
    }

    @GetMapping("/{id}/favouritePlaygrounds/{playgroundId}")
    public ResponseEntity<Boolean> getIfPlaygroundIsFavourite(@PathVariable(value = "id") int userId, @PathVariable(value = "playgroundId") int playgroundId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Playground playground = playgroundRepository.findById(playgroundId)
                .orElseThrow(() -> new ResourceNotFoundException("Playground with id " + playgroundId + " not found"));

        Boolean favourite = user.getFavouritePlaygrounds().contains(playground);
        return ResponseEntity.ok(favourite);
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") int userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{username}", produces = "application/json")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public User createUser(@Valid @RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PutMapping("/{id}/favouritePlaygrounds/{playgroundId}")
    public ResponseEntity<Boolean> togglePlaygroundFavorite(@PathVariable(value = "id") int userId, @PathVariable(value = "playgroundId") int playgroundId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Playground playground = playgroundRepository.findById(playgroundId)
                .orElseThrow(() -> new ResourceNotFoundException("Playground with id " + playgroundId + " not found"));

        boolean favourite;

        if(user.getFavouritePlaygrounds().contains(playground)) {
            user.getFavouritePlaygrounds().remove(playground);
            favourite = false;
        } else {
            user.getFavouritePlaygrounds().add(playground);
            favourite = true;
        }
        userRepository.save(user);

        return ResponseEntity.ok(favourite);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") int userId, @Valid @RequestBody User userDetails)
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
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") int userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
