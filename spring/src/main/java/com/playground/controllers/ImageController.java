package com.playground.controllers;

import com.playground.model.Image;
import com.playground.repository.ImageRepository;
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
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping(value = "/", produces = "application/json")
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Image> getImagesById(@PathVariable(value = "id") int imageId) throws ResourceNotFoundException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image with id " + imageId + " not found"));
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Image createImage(@Valid @RequestBody Image image) {
        return imageRepository.save(image);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable(value = "id") int imageId, @Valid @RequestBody Image imageDetails)
            throws ResourceNotFoundException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image with id " + imageId + " not found"));
        image.setUrl(imageDetails.getUrl());
        image.setDescription(imageDetails.getDescription());
        final Image updatedImage = imageRepository.save(image);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteImage(@PathVariable(value = "id") int imageId) throws ResourceNotFoundException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image with id " + imageId + " not found"));
        imageRepository.delete(image);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}