package com.playground.service.interfaces;

import com.playground.model.entity.Comment;
import com.playground.model.entity.Playground;

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
     * Return all comments of a playground
     *
     * @param playground Playground
     *
     * @return List<Comment>
     */
    List<Comment> getCommentsByPlayground(Playground playground);

    /**
     * Return one comment
     *
     * @param id int
     *
     * @return Comment
     */
    Comment getComment(int id);

    /**
     * Create a comment, update average mark on playground and return it
     *
     * @param playground Playground
     * @param comment Comment
     *
     * @return Comment
     */
    Comment createComment(Playground playground, Comment comment);

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
