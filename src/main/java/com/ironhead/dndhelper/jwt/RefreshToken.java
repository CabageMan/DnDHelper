package com.ironhead.dndhelper.jwt;

import com.ironhead.dndhelper.user.UserInfo;
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
  private UserInfo userInfo;

  public RefreshToken() { }

  public RefreshToken(String token, Instant expiryDate, UserInfo userInfo) {
    this.token = token;
    this.expiryDate = expiryDate;
    this.userInfo = userInfo;
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

  public UserInfo getUser() {
    return userInfo;
  }

  public void setUser(UserInfo userInfo) {
    this.userInfo = userInfo;
  }
}
