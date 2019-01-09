package com.playground.controllers;

import com.playground.model.Playground;
import com.playground.model.Sport;
import com.playground.repository.PlaygroundRepository;
import com.playground.repository.SportRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/playgrounds")
public class PlaygroundController {

    @Autowired
    private PlaygroundRepository playgroundRepository;

    @Autowired SportRepository sportRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Playground>> getAllPlaygrounds() {
        ArrayList<Playground> listPlaygrounds = new ArrayList<>();
        for (Playground playground : playgroundRepository.findAll()) {
            listPlaygrounds.add(playground);
        }
        return new ResponseEntity<>(listPlaygrounds,HttpStatus.OK);
    }

    @GetMapping(value = "/search/{keywords}", produces = "application/json")
    public ResponseEntity<List<Playground>> search(@PathVariable(value = "keywords") String keywords) {
        ArrayList<Playground> listPlaygrounds = new ArrayList<>();
        for (Playground playground : playgroundRepository.search(keywords)) {
            listPlaygrounds.add(playground);
        }
        return new ResponseEntity<>(listPlaygrounds, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity<Playground> getPlaygroundsById(@PathVariable(value = "id") int playgroundId) throws ResourceNotFoundException {
        Playground playground = playgroundRepository.findById(playgroundId)
                .orElseThrow(() -> new ResourceNotFoundException("Playground with id " + playgroundId + " not found"));
        return new ResponseEntity<>(playground, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public Playground createPlayground() {
        Playground playground = new Playground();
        playground.setCovered(false);
        playground.setLatitude(45.7854453);
        playground.setLongitude(4.8823925);
        playground.setName("Terrains de l'INSA");
        playground.setDescription("Des terrains appartenant aux charlots de l'INSA");
        Set<Sport> sports = new HashSet<>();
        sports.add(sportRepository.findById(1).get());
        sports.add(sportRepository.findById(3).get());
        playground.setSports(sports);
        playground.setAddress("Boulevard Niels Bohr");
        playground.setCity("Villeurbanne");
        return playgroundRepository.save(playground);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playground> updatePlayground(@PathVariable(value = "id") int playgroundId, @Valid @RequestBody Playground playgroundDetails)
            throws ResourceNotFoundException {
        Playground playground = playgroundRepository.findById(playgroundId)
                .orElseThrow(() -> new ResourceNotFoundException("Playground with id " + playgroundId + " not found"));
        playground.setName(playgroundDetails.getName());
        playground.setSurface(playgroundDetails.getSurface());
        playground.setSports(playgroundDetails.getSports());
        playground.setPrivate(playgroundDetails.isPrivate());
        playground.setPlayers(playgroundDetails.getPlayers());
        playground.setLongitude(playgroundDetails.getLongitude());
        playground.setLatitude(playgroundDetails.getLatitude());
        playgroundDetails.setImage(playgroundDetails.getImage());
        playground.setDescription(playgroundDetails.getDescription());
        playground.setCovered(playgroundDetails.isCovered());
        playground.setAverageMark(playgroundDetails.getAverageMark());
        final Playground updatedPlayground = playgroundRepository.save(playground);
        return ResponseEntity.ok(updatedPlayground);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deletePlayground(@PathVariable(value = "id") int playgroundId) throws ResourceNotFoundException {
        Playground playground = playgroundRepository.findById(playgroundId)
                .orElseThrow(() -> new ResourceNotFoundException("Playground with id " + playgroundId + " not found"));
        playgroundRepository.delete(playground);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}