package com.playground.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.model.entity.ReportPlayground;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ReportPlaygroundDto {

    private int id;

    private UserDto author;

    private PlaygroundDto playground;

    private String description;

    public ReportPlaygroundDto(ReportPlayground reportPlayground) {
        this.id = reportPlayground.getId();
        this.author = new UserDto(reportPlayground.getAuthor().getId(), reportPlayground.getAuthor().getUsername());
        this.playground = new PlaygroundDto(reportPlayground.getPlayground().getId(), reportPlayground.getPlayground().getName());
        this.description = reportPlayground.getDescription();
    }
}
