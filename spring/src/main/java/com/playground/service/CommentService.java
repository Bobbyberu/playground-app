package com.playground.service;

import com.playground.model.Comment;
import com.playground.repository.CommentRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class CommentService
 */
@Service
public class CommentService implements ICommentService {

    /** CommentRepository commentRepository */
    private final CommentRepository commentRepository;

    /**
     * CommentService Constructor
     *
     * @param commentRepository CommentRepository
     */
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comments::add);

        return comments;
    }

    @Override
    public Comment getComment(int id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));

        System.out.println(comment);

        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(int id, Comment comment) {
        Comment editComment = getComment(id);

        editComment.setArchived(comment.isArchived());
        editComment.setComment(comment.getComment());
        editComment.setMark(comment.getMark());

        return commentRepository.save(editComment);
    }

    @Override
    public void deleteComment(int id) {
        Comment comment = getComment(id);
        commentRepository.delete(comment);
    }

    @Override
    public Comment archivedComment(int id) {
        Comment comment = getComment(id);

        comment.setArchived(true);

        return commentRepository.save(comment);
    }
}
