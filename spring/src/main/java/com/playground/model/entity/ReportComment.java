package com.playground.model.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
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

    public ReportComment() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
