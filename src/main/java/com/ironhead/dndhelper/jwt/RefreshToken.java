package com.ironhead.dndhelper.jwt;

import com.ironhead.dndhelper.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity(name = "RefreshToken")
@Table(name = "REFRESH_TOKENS")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String token;
  private Instant expiryDate;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public RefreshToken() { }

  public RefreshToken(String token, Instant expiryDate, User user) {
    this.token = token;
    this.expiryDate = expiryDate;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Instant getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Instant expiryDate) {
    this.expiryDate = expiryDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
