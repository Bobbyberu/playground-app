package com.playground.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.model.entity.Comment;
import com.playground.model.entity.User;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    @Getter
    private int id;

    @Getter
    private String comment;

    @Getter
    private int playgroundId;

    @Getter
    private UserDto author;

    @Getter
    private double mark;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.playgroundId = comment.getPlayground().getId();
        this.mark = comment.getMark();

        this.author = new UserDto(comment.getAuthor().getId(), comment.getAuthor().getUsername());
    }

    // create summary of comment for ReportCommentDto
    public CommentDto(int id, int playgroundId, String comment, User author) {
        this.id = id;
        this.playgroundId = playgroundId;
        this.comment = comment;
        this.author = new UserDto(author.getId(), author.getUsername());
    }
}
