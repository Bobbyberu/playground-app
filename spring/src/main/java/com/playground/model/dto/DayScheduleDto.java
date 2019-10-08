package com.playground.model.dto;

import com.playground.model.entity.Schedule;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class DayScheduleDto {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.FRANCE);

    private DayOfWeek day;

    private String opening;

    private String closure;

    public DayScheduleDto(Schedule schedule) {
        this.day = schedule.getDay();

        this.opening = FORMATTER.format(schedule.getOpening());

        this.closure = FORMATTER.format(schedule.getClosure());
    }

}
