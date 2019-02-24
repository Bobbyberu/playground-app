package com.playground.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.model.entity.Playground;
import com.playground.model.entity.Sport;
import com.playground.model.entity.User;
import lombok.Getter;
import lombok.Setter;

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

    // to get playground detail
    public PlaygroundDto(Playground playground) {
        this.id = playground.getId();
        this.name = playground.getName();
        this.isPrivate = playground.isPrivate();
        this.covered = playground.isCovered();
        this.description = playground.getDescription();

        this.latitude = playground.getLatitude();
        this.longitude = playground.getLongitude();

        this.surface = playground.getSurface();
        this.averageMark = playground.getAverageMark();
        this.players = playground.getPlayers();
        this.sports = playground.getSports();

        this.city = playground.getCity();
        this.address = playground.getAddress();
    }

    // to display icon in map
    public PlaygroundDto(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // create summary of playground to use in report
    public PlaygroundDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // to get favourites playgrounds
    public PlaygroundDto(int id, double latitude, double longitude, String name, String address) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
    }
}
