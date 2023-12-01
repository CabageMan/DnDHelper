package com.ironhead.dndhelper.user;

import java.util.Set;

public class UserResponseDto {
  private Long id;
  private String username;
  private Set<UserRole> roles;

  public UserResponseDto() { }

  public UserResponseDto(Long id, String username, Set<UserRole> roles) {
    this.id = id;
    this.username = username;
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

  public Set<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }
}
