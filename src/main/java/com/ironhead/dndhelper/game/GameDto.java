package com.ironhead.dndhelper.game;

// Game Data Transfer Object
public class GameDto {
  private Long id;
  private String name;

  public GameDto() { }

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
}
