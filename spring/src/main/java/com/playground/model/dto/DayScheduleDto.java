package com.playground.model.dto;

import lombok.Getter;

import java.time.DayOfWeek;

@Getter
public class DayScheduleDto {

    private DayOfWeek day;

    private String opening;

    private String closure;

    public DayScheduleDto(DayOfWeek day, String opening, String closure) {
        this.day = day;
        this.opening = opening;
        this.closure = closure;
    }

}
