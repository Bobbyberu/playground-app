package com.playground.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique=true)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Sport> getFavouriteSports() {
        return favouriteSports;
    }

    public void setFavouriteSports(Set<Sport> favouriteSports) {
        this.favouriteSports = favouriteSports;
    }

    @JsonIgnore
    public Set<Playground> getFavouritePlaygrounds() {
        return favouritePlaygrounds;
    }

    public void setFavouritePlaygrounds(Set<Playground> favouritePlaygrounds) {
        this.favouritePlaygrounds = favouritePlaygrounds;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public Sport getPlaying() {
        return playing;
    }

    public void setPlaying(Sport playing) {
        this.playing = playing;
    }
}
