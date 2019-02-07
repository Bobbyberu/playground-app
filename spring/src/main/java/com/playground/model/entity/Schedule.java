package com.playground.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Date;

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

    private String opening;

    private String closure;

    private DayOfWeek day;

    public Schedule() {
    }
}
