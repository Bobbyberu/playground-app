package com.playground.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "playground")
@EntityListeners(AuditingEntityListener.class)
public class Playground {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String description;

    private boolean isPrivate;

    private boolean covered;

    private double latitude;
    private double longitude;

    private String surface;

    private double averageMark;

    private String imageName;

    @OneToMany
    private Set<User> players;

    @ManyToMany
    private Set<Sport> sports;

    private String city;
    private String address;

    public Playground(String name, boolean isPrivate, boolean covered, double latitude, double longitude, String surface, String description, String city, String address) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.covered = covered;
        this.latitude = latitude;
        this.longitude = longitude;
        this.surface = surface;
        this.description = description;
        this.city = city;
        this.address = address;
    }

    public Playground() {
    }
}
