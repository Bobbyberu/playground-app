package com.playground.model.response;

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
    private User author;

    @Getter
    private double mark;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.playgroundId = comment.getPlayground().getId();
        this.mark = comment.getMark();

        this.author = new User();
        this.author.setId(comment.getAuthor().getId());
        this.author.setUsername(comment.getAuthor().getUsername());
    }
}
