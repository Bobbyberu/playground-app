package com.playground.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String mail;
    private Date birthDate;
    private String password;
    @OneToOne
    private Image avatar;
    @OneToMany
    private Set<User> friends;
    @OneToMany
    private Set<Sport> favouriteSports;
    @OneToMany
    private Set<Playground> favouritePlaygrounds;
    private String city;
    @OneToOne
    private Role role;
    private boolean enabled;
    private boolean archived;
    private boolean banned;

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
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
}
