package com.ironhead.dndhelper.dto;

// Party Data Transfer Object
public class PartyData {
  private Long id;
  private String name;

  public PartyData() { }

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
