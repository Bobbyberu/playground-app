package com.playground.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.model.entity.Playground;
import com.playground.model.entity.Sport;
import com.playground.model.entity.User;
import lombok.Getter;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class PlaygroundDto {

    private int id;

    private String name;

    private Boolean isPrivate;

    private Boolean covered;

    private double latitude;

    private double longitude;

    private String surface;

    private String description;

    private Double averageMark;

    private Set<User> players;

    private Set<Sport> sports;

    private String city;

    private String address;

    public PlaygroundDto(Playground playground) {
        this.id = playground.getId();
        this.name = playground.getName();
        this.isPrivate = playground.isPrivate();
        this.covered = playground.isCovered();

        this.latitude = playground.getLatitude();
        this.longitude = playground.getLongitude();

        this.surface = playground.getSurface();
        this.averageMark = playground.getAverageMark();
        this.players = playground.getPlayers();
        this.sports = playground.getSports();

        this.city = playground.getCity();
        this.address = playground.getAddress();
    }

    public PlaygroundDto(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
