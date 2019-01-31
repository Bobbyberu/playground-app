package com.playground.model.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "verification_token")
@EntityListeners(AuditingEntityListener.class)
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String token;

    @OneToOne
    private User user;

    private Date expiryDate;

    public VerificationToken() {
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.token = UUID.randomUUID().toString();
    }

    public VerificationToken(User user) {
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.token = UUID.randomUUID().toString();
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}