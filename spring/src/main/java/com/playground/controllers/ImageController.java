package com.playground.controllers;

import com.playground.model.Image;
import com.playground.model.Playground;
import com.playground.repository.ImageRepository;
import com.playground.repository.PlaygroundRepository;
import com.playground.service.StorageService;
import com.playground.utils.ResourceNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private PlaygroundRepository playgroundRepository;

    @GetMapping(value = "/playground/{id}", produces = "image/jpg")
    public @ResponseBody ResponseEntity<byte[]> getPlaygroundImage(@PathVariable(value = "id") int playgroundId)  {
        StorageService storageService = new StorageService();

        byte[] image = null;

        try {
            Playground playground = playgroundRepository.findById(playgroundId).orElseThrow(() -> new ResourceNotFoundException("Playground not found"));

            if (playground.getImageName() == null) return null;

            String filePath = StorageService.imageDir + playground.getImageName();

            image = storageService.retrieve(filePath);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(image, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping(value = "/playground/{id}")
    public @ResponseBody ResponseEntity addPlaygroundImage(@PathVariable(value = "id") int playgroundId, @RequestParam("file") MultipartFile file) {
        StorageService storageService = new StorageService();

        System.out.println("-- addPlaygroundImage()");
        try {
            Playground playground = playgroundRepository.findById(playgroundId).orElseThrow(() -> new ResourceNotFoundException("Playground not found"));

            System.out.println(file.getOriginalFilename());
            String fileName = "playground_" + playgroundId + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            String filePath = StorageService.imageDir + fileName;
            storageService.store(file,filePath);

            playground.setImageName(fileName);
            playgroundRepository.save(playground);

            return new ResponseEntity<>(null, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
