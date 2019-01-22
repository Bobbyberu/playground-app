package com.playground.controllers;

import com.playground.model.Playground;
import com.playground.model.User;
import com.playground.service.PlaygroundService;
import com.playground.service.TokenAuthenticationService;
import com.playground.service.UserService;
import com.playground.storage.StorageService;
import com.playground.utils.ResourceNotFoundException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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

    private final StorageService storageService;

    /**
     * UserController Constructor
     *
     * @param userService       UserService
     * @param playgroundService PlaygroundService
     * @param storageService StorageService
     */
    @Autowired
    public UserController(UserService userService, PlaygroundService playgroundService, StorageService storageService) {
        this.userService = userService;
        this.playgroundService = playgroundService;
        this.storageService = storageService;
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
     * [GET] Return an user by his mail
     *
     * @param mail String
     *
     * @return ResponseEntity
     * @throws ResourceNotFoundException User not found
     */
    @GetMapping(value = "/mail/{mail}", produces = "application/json")
    public ResponseEntity<User> getUserByMail(@PathVariable(value = "mail") String mail) throws ResourceNotFoundException {
        User user = userService.getUserByMail(mail);

        if (user == null) {
            throw new ResourceNotFoundException("User with mail " + mail + " not found");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     * [GET] Return a distinct list of playgrounds
     *
     * @param id int
     * @return ResponseEntity
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
     * @return ResponseEntity
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
     * @return ResponseEntity
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
     * @return ResponseEntity
     */
    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.signup(user), HttpStatus.CREATED);
    }

    /**
     * [PUT] add/remove a user's favourite playground
     *
     * @param userId int
     * @param playgroundId int
     * @return ResponseEntity
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
     * @return ResponseEntity
     * @throws ResourceNotFoundException User not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) throws ResourceNotFoundException {
        User currentUser = userService.getUser(id);

        if (currentUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        return new ResponseEntity<>(userService.updateUserProfile(id, currentUser, user), HttpStatus.OK);
    }

    /**
     * [PUT] Ban an user
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException User not found
     */
    @PutMapping("/ban/{id}")
    public ResponseEntity<User> banUser(@PathVariable("id") int id) throws ResourceNotFoundException {

        User currentUser = userService.getUser(id);

        if (currentUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        User user = userService.banUser(currentUser);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * [DELETE] Delete an user
     *
     * @param id int
     * @return ResponseEntity
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

    /**
     * [GET] get image for corresponding user
     *
     * @param userMail
     * @return image
     */
    @GetMapping(value = "{userMail}/image", produces = "image/*")
    @ResponseBody
    public ResponseEntity<byte[]> getUserImage(@PathVariable String userMail) {
        try {
            User user = userService.getUserByMail(userMail);
            String filename = user.getAvatarName();

            Resource file;

            // if playground has no image yet
            if (filename != null) {
                file = storageService.loadUserAsResource(filename);

                // in case file is not found or does not exist
                if (file == null) {
                    file = storageService.loadUserAsResource("default_avatar");
                }
            } else {
                file = storageService.loadUserAsResource("default_avatar");
            }

            InputStream is = new FileInputStream(file.getFile());

            // Prepare buffered image.
            BufferedImage img = ImageIO.read(is);

            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            // Write to output stream
            ImageIO.write(img, "png", bao);

            return new ResponseEntity<>(bao.toByteArray(), HttpStatus.OK);
            //return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * [POST] Upload image for user
     *
     * @param request
     * @param file
     * @return ResponseEntity
     */
    @PostMapping("/image")
    public ResponseEntity<?> handleUserImageUpload(HttpServletRequest request, @RequestBody MultipartFile file) {

        String token = request.getHeader("authorization");

        // get user mail from token
        String userMail = Jwts.parser().setSigningKey(TokenAuthenticationService.SECRET)
                .parseClaimsJws(token.replace(TokenAuthenticationService.TOKEN_PREFIX, "")).getBody()
                .getSubject();
        User user = userService.getUserByMail(userMail);
        int id = user.getId();

        if (user.getAvatarName() == null) {
            // update user avatar name
            user.setAvatarName("user" + id);
            userService.updateUser(id, user);
        }
        String filename = user.getAvatarName();
        storageService.storeUser(file, filename);

        return ResponseEntity.ok(true);
    }

}
