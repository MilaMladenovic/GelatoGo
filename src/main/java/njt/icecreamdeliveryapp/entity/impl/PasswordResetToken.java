/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.entity.impl;

/**
 *
 * @author milam
 */
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    private String token; // čuvamo UUID kao PK

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable=false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean used = false;

    public static PasswordResetToken of(User u, long ttlSeconds) {
        PasswordResetToken t = new PasswordResetToken();
        t.token = UUID.randomUUID().toString();
        t.user = u;
        t.expiresAt = Instant.now().plusSeconds(ttlSeconds);
        t.used = false;
        return t;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
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

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

}
