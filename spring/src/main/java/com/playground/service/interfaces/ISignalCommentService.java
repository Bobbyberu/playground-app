package com.playground.service.interfaces;

import com.playground.model.SignalComment;

import java.util.List;

/**
 * Interface ISignalCommentService
 */
public interface ISignalCommentService {

    /**
     * Return all signal comments
     *
     * @return List<SignalComment>
     */
    List<SignalComment> getSignalComments();

    /**
     * Return one signal comment
     *
     * @param id int
     *
     * @return SignalComment
     */
    SignalComment getSignalComment(int id);

    /**
     * Create a signal comment and return it
     *
     * @param signalComment SignalComment
     *
     * @return SignalComment
     */
    SignalComment createSignalComment(SignalComment signalComment);

    /**
     * Update a signal comment and return it
     *
     * @param id int
     * @param signalComment SignalComment
     *
     * @return SignalComment
     */
    SignalComment updateSignalComment(int id, SignalComment signalComment);

    /**
     * Delete a signal comment
     *
     * @param signalComment SignalComment
     */
    void deleteSignalComment(SignalComment signalComment);
}
