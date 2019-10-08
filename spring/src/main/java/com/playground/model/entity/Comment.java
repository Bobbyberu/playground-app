package com.playground.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Playground playground;

    @ManyToOne
    private User author;

    private boolean archived;

    private String comment;

    private double mark;

    public Comment(Playground playground, User author, String comment, double mark) {
        this.playground = playground;
        this.author = author;
        this.archived = false;
        this.comment = comment;
        this.mark = mark;
    }

    public Comment() {
    }
}
