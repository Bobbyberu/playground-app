package com.playground.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "session")
@EntityListeners(AuditingEntityListener.class)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne
    private User creator;

    @ManyToOne
    private Playground playground;

    @OneToMany
    private Set<User> participants;

    private boolean isPrivate;

    @ManyToOne
    private Sport sport;

    private int maxPLayers;
    
    private Date date;

    public Session() {
    }
}
