package com.playground.controllers;

import com.playground.model.*;
import com.playground.service.*;
import com.playground.storage.StorageService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
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
@RequestMapping("/playgrounds")
public class PlaygroundController {

    private final PlaygroundService playgroundService;

    private final StorageService storageService;

    private final UserService userService;

    private final SportService sportService;

    private final CommentService commentService;

    private final ReportPlaygroundService reportPlaygroundService;

    /**
     * PlaygroundController Constructor
     *
     * @param playgroundService PlaygroundService
     */
    @Autowired
    public PlaygroundController(PlaygroundService playgroundService, StorageService storageService,
                                UserService userService, SportService sportService, CommentService commentService,
                                ReportPlaygroundService reportPlaygroundService) {
        this.playgroundService = playgroundService;
        this.storageService = storageService;
        this.userService = userService;
        this.sportService = sportService;
        this.commentService = commentService;
        this.reportPlaygroundService = reportPlaygroundService;
    }

    /**
     * [GET] Return all playgrounds
     *
     * @return ResponseEntity
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
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Playground> getPlaygroundsById(@PathVariable("id") int id) throws ResourceNotFoundException {
        Playground playground = playgroundService.getPlayground(id);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + id + " not found");
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
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Playground> updatePlayground(@PathVariable(value = "id") int id, @RequestBody Playground playground) throws ResourceNotFoundException {
        Playground currentPlayground = playgroundService.getPlayground(id);

        if (currentPlayground == null) {
            throw new ResourceNotFoundException("Playground with id " + id + " not found");
        }

        return new ResponseEntity<>(playgroundService.updatePlayground(id, playground), HttpStatus.OK);
    }

    /**
     * [PUT] Add a player on the playground
     *
     * @param idPlayground int
     * @param idUser Playground
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @PutMapping("/{idPlayground}/player/{idUser}/sport/{idSport}/add")
    public ResponseEntity<Playground> addPlayerToPlayground(@PathVariable(value = "idPlayground") int idPlayground,
                                                            @PathVariable(value = "idUser") int idUser,
                                                            @PathVariable(value = "idSport") int idSport) throws ResourceNotFoundException {
        Playground currentPlayground = playgroundService.getPlayground(idPlayground);
        if (currentPlayground == null) {
            throw new ResourceNotFoundException("Playground with id " + idPlayground + " not found");
        }

        User user = userService.getUser(idUser);
        if (user == null) {
            throw new ResourceNotFoundException("User with id " + idUser + " not found");
        }

        Sport sport = sportService.getSport(idSport);
        if (sport == null) {
            throw new ResourceNotFoundException("Sport with id " + idSport + " not found");
        }
        else if (!currentPlayground.getSports().contains(sport)) {
            return new ResponseEntity<>(currentPlayground,HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(playgroundService.addPlayerToPlayground(currentPlayground, user, sport), HttpStatus.OK);
    }

    /**
     * [PUT] Remove a player from the playground
     *
     * @param idPlayground int
     * @param idUser Playground
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @PutMapping("/{idPlayground}/player/{idUser}/remove")
    public ResponseEntity<Playground> removePlayerFromPlayground(@PathVariable(value = "idPlayground") int idPlayground, @PathVariable(value = "idUser") int idUser) throws ResourceNotFoundException {
        Playground playground = playgroundService.getPlayground(idPlayground);
        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + idPlayground + " not found");
        }

        User user = userService.getUser(idUser);
        if (user == null) {
            throw new ResourceNotFoundException("User with id " + idUser + " not found");
        }

        return new ResponseEntity<>(playgroundService.removePlayerFromPlayground(playground, user), HttpStatus.OK);
    }

    /**
     * [PUT] Remove a player from the playground
     *
     * @param idPlayground int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @GetMapping("/{idPlayground}/players")
    public ResponseEntity<List<User>> getPlayersOnPlayground(@PathVariable(value = "idPlayground") int idPlayground) throws ResourceNotFoundException {
        Playground playground = playgroundService.getPlayground(idPlayground);
        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + idPlayground + " not found");
        }

        return new ResponseEntity<>(playgroundService.getPlayersOnPlayground(playground), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a playground and linked entities (comments, comment reports and playground reports)
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayground(@PathVariable("id") int id) throws ResourceNotFoundException {
        Playground currentPlayground = playgroundService.getPlayground(id);

        if (currentPlayground == null) {
            throw new ResourceNotFoundException("Playground with id " + id + " not found");
        }

        List<Comment> comments = commentService.getCommentsByPlayground(currentPlayground);

        for (Comment comment : comments) {
            commentService.deleteComment(comment);
        }

        List<ReportPlayground> playgroundReports = reportPlaygroundService
                .getReportPlaygroundsByPlayground(currentPlayground);

        for (ReportPlayground reportPlayground : playgroundReports) {
            reportPlaygroundService.deleteReportPlayground(reportPlayground);
        }

        playgroundService.deletePlayground(currentPlayground);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * [GET] Return all playgrounds which are linked with the keyword (sport, name ...)
     *
     * @param keyword String
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/search/{keyword}", produces = "application/json")
    public ResponseEntity<List<Playground>> search(@PathVariable("keyword") String keyword) {
        return new ResponseEntity<>(playgroundService.searchPlaygroundByKeyword(keyword), HttpStatus.OK);
    }

    /**
     * [GET] get image for corresponding playground
     *
     * @param playgroundId id of playground
     *
     * @return image
     */
    @GetMapping(value = "/{playgroundId}/image", produces = "image/png")
    @ResponseBody
    public ResponseEntity<byte[]> getPlaygroundImage(@PathVariable int playgroundId) {
        try {
            String imageName = playgroundService.getPlayground(playgroundId).getImageName();

            Resource file;

            // if playground has no image yet
            if(imageName != null) {
                file = storageService.loadPlaygroundAsResource(imageName,false);

                // in case file is not found or does not exist
                if(file == null) {
                    file = storageService.loadPlaygroundAsResource("default_playground", true);
                }
            }else {
                file = storageService.loadPlaygroundAsResource("default_playground", true);
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
     * [POST] Upload image for playground
     *
     * @param playgroundId
     * @param file
     *
     * @return ResponseEntity
     */
    @PostMapping("/{playgroundId}/image")
    public ResponseEntity<?> handlePlaygroundImageUpload(@PathVariable int playgroundId, @RequestBody MultipartFile file) {
        String filename = "playground" + playgroundId;

        Playground playground = playgroundService.getPlayground(playgroundId);
        playground.setImage(filename);
        playgroundService.updatePlayground(playgroundId, playground);

        storageService.storePlayground(file, filename);

        return ResponseEntity.ok(true);
    }
}
