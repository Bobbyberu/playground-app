package com.playground.model.dto;

import com.playground.model.entity.Session;
import com.playground.model.entity.Sport;
import lombok.Getter;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class SessionDto {

    private int id;

    private String name;

    private UserDto creator;

    private int playgroundId;

    private boolean isPrivate;

    private Sport sport;

    private Set<UserDto> participants;

    private int maxPlayers;

    private Date date;

    public SessionDto(Session session) {
        this.id = session.getId();
        this.name = session.getName();
        this.creator = new UserDto(session.getCreator().getId(), session.getCreator().getUsername());
        this.playgroundId = session.getPlayground().getId();
        this.isPrivate = session.isPrivate();
        this.sport = session.getSport();
        this.maxPlayers = session.getMaxPLayers();
        this.date = session.getDate();

        Set<UserDto> participants = session.getParticipants().stream()
                .map(u -> new UserDto(u.getId(), u.getUsername()))
                .collect(Collectors.toSet());
        this.participants = participants;
    }
}
