package com.playground.model.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class CompleteScheduleDto {

    private int playgroundId;

    private Set<DayScheduleDto> days;

    public CompleteScheduleDto(int playgroundId, Set<DayScheduleDto> days) {
        this.playgroundId = playgroundId;
        this.days = days;
    }

}
