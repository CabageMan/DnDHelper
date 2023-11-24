package com.ironhead.dndhelper.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameTests {

  @Autowired
  private GameRepository gameRepository;

  @Test
  public void repositoryShouldSaveAndRetrieveEntity() {
    Game savedEntity = gameRepository.save(new Game("Test Game"));
    Optional<Game> retrievedEntity = gameRepository.findById(savedEntity.getId());
    assertNotNull(savedEntity);
    assertEquals(savedEntity.toString(), retrievedEntity.orElseThrow().toString());
  }
}
