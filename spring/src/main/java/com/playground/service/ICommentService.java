package com.playground.service;

import com.playground.model.Comment;

import java.util.List;

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
     * @return Comment
     */
    Comment getComment(int id);

    /**
     * Create a comment and return it
     *
     * @param comment Comment
     * @return Comment
     */
    Comment createComment(Comment comment);

    /**
     * Update a comment and return it
     *
     * @param id int
     * @param comment Comment
     * @return Comment
     */
    Comment updateComment(int id, Comment comment);

    /**
     * Delete a comment
     *
     * @param id int
     */
    void deleteComment(int id);

    /**
     * Archived a comment
     *
     * @param id int
     * @return Comment
     */
    Comment archivedComment(int id);
}
