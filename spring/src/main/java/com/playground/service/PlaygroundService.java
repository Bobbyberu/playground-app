package com.playground.service;

import com.playground.model.Playground;
import com.playground.repository.PlaygroundRepository;
import com.playground.service.interfaces.IPlaygroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class PlaygroundService
 */
@Service
public class PlaygroundService implements IPlaygroundService {

    /** PlaygroundRepository playgroundRepository */
    private final PlaygroundRepository playgroundRepository;

    /**
     * PlaygroundService Constructor
     *
     * @param playgroundRepository PlaygroundRepository
     */
    @Autowired
    public PlaygroundService(PlaygroundRepository playgroundRepository) {
        this.playgroundRepository = playgroundRepository;
    }

    @Override
    public List<Playground> getPlaygrounds() {
        List<Playground> playgrounds = new ArrayList<>();
        playgroundRepository.findAll().forEach(playgrounds::add);

        return  playgrounds;
    }

    @Override
    public Playground getPlayground(int id) {
        return playgroundRepository.findById(id).orElse(null);
    }

    @Override
    public Playground createPlayground(Playground playground) {
        return playgroundRepository.save(playground);
    }

    @Override
    public Playground updatePlayground(int id, Playground playground) {
        playground.setId(id);
        return playgroundRepository.save(playground);
    }

    @Override
    public void deletePlayground(Playground playground) {
        playgroundRepository.delete(playground);
    }

    @Override
    public List<Playground> searchPlaygroundByKeyword(String keyword) {
        List<Playground> playgrounds = new ArrayList<>();
        playgroundRepository.search(keyword).forEach(playgrounds::add);

        return playgrounds;
    }
}
