package com.playground.service.interfaces;

import com.playground.model.SignalPlayground;

import java.util.List;

/**
 * Interface ISignalPlaygroundService
 */
public interface ISignalPlaygroundService {

    /**
     * Return all signal playgrounds
     *
     * @return List<SignalPlayground>
     */
    List<SignalPlayground> getSignalPlaygrounds();

    /**
     * Return one signal playground
     *
     * @param id int
     *
     * @return SignalPlayground
     */
    SignalPlayground getSignalPlayground(int id);

    /**
     * Create a signal playground and return it
     *
     * @param signalPlayground SignalPlayground
     *
     * @return SignalPlayground
     */
    SignalPlayground createSignalPlayground(SignalPlayground signalPlayground);

    /**
     * Update a signal playground and return it
     *
     * @param id int
     * @param signalPlayground SignalPlayground
     *
     * @return SignalPlayground
     */
    SignalPlayground updateSignalPlayground(int id, SignalPlayground signalPlayground);

    /**
     * Delete a signal playground
     *
     * @param signalPlayground SignalPlayground
     */
    void deleteSignalPlayground(SignalPlayground signalPlayground);
}
