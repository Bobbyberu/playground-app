package com.playground.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String username;

    private String mail;

    private Date birthDate;

    private String password;

    private String avatarName;

    @ManyToMany
    private Set<User> friends;

    @ManyToMany
    private Set<Sport> favouriteSports;

    @ManyToMany
    private Set<Playground> favouritePlaygrounds;

    private String city;

    @ManyToOne
    private Role role;

    private boolean enabled;

    private boolean archived;

    private boolean banned;

    @ManyToOne
    private Sport playing;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> liste = new ArrayList<>();
        if (this.role.getName().equals("ROLE_ADMIN")) {
            liste.add(new Role("ROLE_USER"));
        }
        liste.add(this.role);
        return liste;
    }
}
