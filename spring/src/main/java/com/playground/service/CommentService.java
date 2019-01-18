package com.playground.service;

import com.playground.model.Comment;
import com.playground.model.Playground;
import com.playground.repository.CommentRepository;
import com.playground.service.interfaces.ICommentService;
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
    public List<Comment> getCommentsByPlayground(Playground playground) {
        return commentRepository.getByPlayground(playground);
    }

    @Override
    public Comment getComment(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment getCommentByPlayground(Playground playground, int id) {
        return commentRepository.getOneByPlayground(playground, id);
    }

    @Override
    public Comment createComment(Playground playground, Comment comment) {
        comment.setPlayground(playground);

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(int id, Comment comment) {
        comment.setId(id);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment archivedComment(Comment comment) {
        comment.setArchived(true);

        return commentRepository.save(comment);
    }
}
