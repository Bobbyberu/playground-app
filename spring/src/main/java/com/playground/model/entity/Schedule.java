package com.playground.model.entity;

import com.playground.model.wrapper.ScheduleWrapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "schedule")
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Playground playground;

    private LocalTime opening;

    private LocalTime closure;

    private DayOfWeek day;

    public Schedule(ScheduleWrapper scheduleWrapper) {
        this.opening = LocalTime.of(scheduleWrapper.getOpeningHour(), scheduleWrapper.getOpeningMinute());
        this.closure = LocalTime.of(scheduleWrapper.getClosureHour(), scheduleWrapper.getClosureMinute());
        this.day = scheduleWrapper.getDay();
    }

    public Schedule() {}
}
