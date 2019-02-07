package com.playground.model.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CompleteScheduleDto {

    private int playgroundId;

    public List<DayScheduleDto> days;

    public CompleteScheduleDto(int playgroundId, List<DayScheduleDto> days) {
        this.playgroundId = playgroundId;
        this.days = days;
    }

}
