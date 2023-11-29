package com.ironhead.dndhelper.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "User") // Available by setting naming strategy in application.properties.
@Table(name = "USERS")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;
  private String username;

  @JsonIgnore
  private String password;

  // Read what it means =)
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<UserRole> roles = new HashSet<>();

  public User() { }

  public User(String username, String password, Set<UserRole> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "User{" + "username='" + username + "'" + "roles='" + roles.toString() + "'" + "}";
  }
}
