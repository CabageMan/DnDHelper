package com.ironhead.dndhelper.game;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "Game") // Available by setting naming strategy in application.properties.
@EntityListeners(AuditingEntityListener.class)
@Table(name = "GAMES")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Column(updatable = false, nullable = false)
  @CreatedDate
  private LocalDateTime createdTime;

  @Column(nullable = false)
  @LastModifiedDate
  private LocalDateTime updatedTime;

  public Game() { }

  public Game(String name) {
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

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(LocalDateTime createdTime) {
    this.createdTime = createdTime;
  }

  public LocalDateTime getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(LocalDateTime updatedTime) {
    this.updatedTime = updatedTime;
  }

  @Override
  public String toString() {
    return "Game{" + "name='" + name + "'" + "}";
  }
}