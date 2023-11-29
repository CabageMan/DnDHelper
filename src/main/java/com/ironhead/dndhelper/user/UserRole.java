package com.ironhead.dndhelper.user;

import jakarta.persistence.*;

@Entity(name = "UserRole")
@Table(name = "USERROLES")
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  public UserRole() {
  }

  public UserRole(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "UserRole{" + "name='" + name + "'" + "}";
  }
}
