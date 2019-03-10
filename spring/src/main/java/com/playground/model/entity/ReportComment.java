package com.playground.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "report_comment")
@EntityListeners(AuditingEntityListener.class)
public class ReportComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private User author;

    private String description;

    public ReportComment(User author, Comment comment, String description) {
        this.author = author;
        this.comment = comment;
        this.description = description;
    }

    public ReportComment() {
    }
}
