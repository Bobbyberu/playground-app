package com.playground.service;

import com.playground.model.entity.Sport;
import com.playground.repository.SportRepository;
import com.playground.service.interfaces.ISportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class SportService
 */
@Service
public class SportService implements ISportService {

    /** SportRepository sportRepository */
    private final SportRepository sportRepository;

    /**
     * SportService Constructor
     *
     * @param sportRepository SportRepository
     */
    @Autowired
    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    @Override
    public List<Sport> getSports() {
        List<Sport> sports = new ArrayList<>();
        sportRepository.findAll().forEach(sports::add);

        return sports;
    }

    @Override
    public Sport getSport(int id) {
        return sportRepository.findById(id).orElse(null);
    }

    @Override
    public Sport createSport(Sport sport) {
        return sportRepository.save(sport);
    }

    @Override
    public Sport updateSport(int id, Sport sport) {
        sport.setId(id);
        return sportRepository.save(sport);
    }

    @Override
    public void deleteSport(Sport sport) {
        sportRepository.delete(sport);
    }
}
