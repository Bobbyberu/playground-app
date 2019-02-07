package com.playground.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "sport")
@EntityListeners(AuditingEntityListener.class)
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String symbol;

    public Sport(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public Sport() {
    }
}
