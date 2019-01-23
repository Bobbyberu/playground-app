package com.playground.controllers;

import com.playground.model.Playground;
import com.playground.service.PlaygroundService;
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

/**
 * Class PlaygroundController
 */
@RestController
@RequestMapping("/playgrounds")
public class PlaygroundController {

    private final PlaygroundService playgroundService;

    private final StorageService storageService;

    /**
     * PlaygroundController Constructor
     *
     * @param playgroundService PlaygroundService
     */
    @Autowired
    public PlaygroundController(PlaygroundService playgroundService, StorageService storageService) {
        this.playgroundService = playgroundService;
        this.storageService = storageService;
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
     * [DELETE] Delete a playground
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
     * @param id of playground
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
                file = storageService.loadPlaygroundAsResource(imageName);

                // in case file is not found or does not exist
                if(file == null) {
                    file = storageService.loadPlaygroundAsResource("default_playground");
                }
            }else {
                file = storageService.loadPlaygroundAsResource("default_playground");
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
