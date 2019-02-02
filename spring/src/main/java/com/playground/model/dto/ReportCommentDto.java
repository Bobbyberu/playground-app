package com.playground.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.model.entity.ReportComment;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ReportCommentDto {

    private int id;

    private CommentDto comment;

    private UserDto author;

    private String description;

    public ReportCommentDto(ReportComment reportComment) {
        this.id = id;
        this.comment = new CommentDto(reportComment.getComment().getId(), reportComment.getComment().getPlayground().getId(),
                reportComment.getComment().getComment(), reportComment.getComment().getAuthor());
        this.author = new UserDto(reportComment.getAuthor().getId(), reportComment.getAuthor().getUsername());
        this.description = description;
    }
}
