package com.ironhead.dndhelper.user;

import java.util.Set;

public class UserRequestDto {
  private Long id;
  private String username;
  private String password;
  private Set<UserRole> roles;

  public UserRequestDto() {
  }

  public UserRequestDto(
          Long id,
          String username,
          String password,
          Set<UserRole> roles
  ) {
    this.id = id;
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
}
