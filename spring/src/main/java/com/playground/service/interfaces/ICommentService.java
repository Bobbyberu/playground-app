package com.playground.service.interfaces;

import com.playground.model.Comment;

import java.util.List;

/**
 * Interface ICommentService
 */
public interface ICommentService {

    /**
     * Return all comments
     *
     * @return List<Comment>
     */
    List<Comment> getComments();

    /**
     * Return one comment
     *
     * @param id int
     *
     * @return Comment
     */
    Comment getComment(int id);

    /**
     * Create a comment and return it
     *
     * @param comment Comment
     *
     * @return Comment
     */
    Comment createComment(Comment comment);

    /**
     * Update a comment and return it
     *
     * @param id int
     * @param comment Comment
     *
     * @return Comment
     */
    Comment updateComment(int id, Comment comment);

    /**
     * Delete a comment
     *
     * @param comment Comment
     */
    void deleteComment(Comment comment);

    /**
     * Archived a comment
     *
     * @param comment Comment
     *
     * @return Comment
     */
    Comment archivedComment(Comment comment);
}