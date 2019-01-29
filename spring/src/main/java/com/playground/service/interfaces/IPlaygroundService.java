package com.playground.service.interfaces;

import com.playground.model.entity.Playground;
import com.playground.model.entity.Sport;
import com.playground.model.entity.User;

import java.util.List;

/**
 * Interface IPlaygroundService
 */
public interface IPlaygroundService {

    /**
     * Return all playground
     *
     * @return List<Playground>
     */
    List<Playground> getPlaygrounds();

    /**
     * Return one playground
     *
     * @param id int
     *
     * @return Playground
     */
    Playground getPlayground(int id);

    /**
     * Create a playground and return it
     *
     * @param playground Playground
     *
     * @return Playground
     */
    Playground createPlayground(Playground playground);

    /**
     * Update a playground and return it
     *
     * @param id int
     * @param playground Playground
     *
     * @return Playground
     */
    Playground updatePlayground(int id, Playground playground);

    /**
     * Add a player on the playground
     *
     * @param playground
     * @param user
     * @return
     */
    Playground addPlayerToPlayground(Playground playground, User user, Sport sport);

    /**
     * Remove a player from the playground
     *
     * @param playground
     * @param user
     * @return
     */
    Playground removePlayerFromPlayground(Playground playground, User user);

    /**
     * Get all the user playing on the playground
     *
     * @param playground
     * @return
     */
    List<User> getPlayersOnPlayground(Playground playground);

    /**
     * Delete a playground
     *
     * @param playground Playground
     */
    void deletePlayground(Playground playground);

    /**
     * Get all playgrounds which are linked with the keyword
     *
     * @param keyword String
     *
     * @return List<Playground>
     */
    List<Playground> searchPlaygroundByKeyword(String keyword);
}
