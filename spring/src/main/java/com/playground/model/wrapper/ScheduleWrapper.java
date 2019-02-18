package com.playground.model.wrapper;

import lombok.Getter;

import java.time.DayOfWeek;

@Getter
public class ScheduleWrapper {

    private int openingHour;

    private int openingMinute;

    private int closureHour;

    private int closureMinute;

    private DayOfWeek day;
}
