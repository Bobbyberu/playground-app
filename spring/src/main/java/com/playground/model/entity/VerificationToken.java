package com.playground.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
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

}