package com.playground.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.model.entity.Role;
import com.playground.model.entity.User;
import lombok.Getter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class UserDto {

    private int id;

    private String username;

    private String mail;

    private Date birthDate;

    private String city;

    private Role role;

    private boolean enabled;

    private boolean archived;

    private boolean banned;

    public UserDto(User user) {
        this.id = user.getId();

        this.username = user.getUsername();
        this.mail = user.getMail();
        this.birthDate = user.getBirthDate();
        this.city = user.getCity();

        this.role = user.getRole();
        this.enabled = user.isEnabled();
        this.archived = user.isArchived();
        this.banned = user.isBanned();
    }
}
