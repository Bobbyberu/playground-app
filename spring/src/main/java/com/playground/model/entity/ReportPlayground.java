package com.playground.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "report_playground")
@EntityListeners(AuditingEntityListener.class)
public class ReportPlayground {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User author;

    @ManyToOne
    private Playground playground;

    private String description;

    public ReportPlayground(User author, Playground playground, String description) {
        this.author = author;
        this.playground = playground;
        this.description = description;
    }

    public ReportPlayground() {
    }
}
