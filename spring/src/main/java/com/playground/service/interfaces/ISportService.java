package com.playground.service.interfaces;

import com.playground.model.Sport;

import java.util.List;

/**
 * Interface ISportService
 */
public interface ISportService {

    /**
     * Return all sports
     *
     * @return List<Sport>
     */
    List<Sport> getSports();

    /**
     * Return one sport
     *
     * @param id int
     *
     * @return Sport
     */
    Sport getSport(int id);

    /**
     * Create a sport and return it
     *
     * @param sport Sport
     *
     * @return Sport
     */
    Sport createSport(Sport sport);

    /**
     * Update a sport and return it
     *
     * @param id int
     * @param sport Sport
     *
     * @return Sport
     */
    Sport updateSport(int id, Sport sport);

    /**
     * Delete a sport
     *
     * @param sport Sport
     */
    void deleteSport(Sport sport);
}
