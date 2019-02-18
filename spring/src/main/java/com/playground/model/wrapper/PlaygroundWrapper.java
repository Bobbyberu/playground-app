package com.playground.model.wrapper;

import com.playground.model.entity.Playground;
import lombok.Getter;

import java.util.Set;

@Getter
public class PlaygroundWrapper {

    private Playground playground;

    private Set<ScheduleWrapper> schedules;
}
