package com.playground.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.model.entity.Playground;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

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

    private Set<UserDto> players;

    private Set<SportDto> sports;

    private String city;

    private String address;

    private CompleteScheduleDto schedules;

    // to get playground detail
    public PlaygroundDto(Playground playground) {
        this.id = playground.getId();
        this.name = playground.getName();
        this.description = playground.getDescription();
        this.isPrivate = playground.isPrivate();
        this.covered = playground.isCovered();
        this.description = playground.getDescription();

        this.latitude = playground.getLatitude();
        this.longitude = playground.getLongitude();

        this.surface = playground.getSurface();
        this.averageMark = playground.getAverageMark();

        if(playground.getSports() != null) {
            Set<SportDto> sports = playground.getSports().stream()
                    .map(s -> new SportDto(s))
                    .collect(Collectors.toSet());
            this.sports = sports;
        }

        if(playground.getPlayers() != null) {
            Set<UserDto> players = playground.getPlayers().stream()
                    .map(p -> new UserDto(p))
                    .collect(Collectors.toSet());
            this.players = players;
        }

        this.city = playground.getCity();
        this.address = playground.getAddress();

        if(playground.getSchedules() != null) {
            Set<DayScheduleDto> daySchedules = playground.getSchedules().stream()
                    .map(s -> new DayScheduleDto(s)).collect(Collectors.toSet());
            this.schedules = new CompleteScheduleDto(playground.getId(), daySchedules);
        }
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
