package com.ironhead.dndhelper.game;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest
public class GameTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private GameService gameService;

  @Test
  // Test saving and getting by ID
  public void repositoryShouldSaveAndRetrieveEntity() {
    Game savedEntity = gameRepository.save(new Game("Test Save Game"));
    Optional<Game> retrievedEntity = gameRepository.findById(savedEntity.getId());
    assertNotNull(savedEntity);

    assertEquals(savedEntity.getName(), retrievedEntity.orElseThrow().getName());
    // Need learn how to test JPA entities
//    assertThat(retrievedEntity.orElseThrow())
//            .usingRecursiveAssertion()
//            .ignoringFields("id")
//            .isEqualTo(savedEntity);
  }

  @Test
  // Test saving and deleting by ID
  public void repositoryShouldSaveAndDeleteEntity() {
    Game savedEntity = gameRepository.save(new Game("Test Delete Game"));
    assertNotNull(savedEntity);
    gameRepository.deleteById(savedEntity.getId());
    Optional<Game> deletedGame = gameRepository.findById(savedEntity.getId());
    assertTrue(deletedGame.isEmpty());
  }

  @Test
  public void repositoryGetAllEntities() {
    Game firstSavedEntity = gameRepository.save(new Game("Test Find All Games 1"));
    Game secondSavedEntity = gameRepository.save(new Game("Test Find All Games 2"));
    Game thirdSavedEntity = gameRepository.save(new Game("Test Find All Games 3"));
    assertNotNull(firstSavedEntity);
    assertNotNull(secondSavedEntity);
    assertNotNull(thirdSavedEntity);

    List<Game> savedGames = new ArrayList<>();
    savedGames.add(firstSavedEntity);
    savedGames.add(secondSavedEntity);
    savedGames.add(thirdSavedEntity);

    List<Game> gamesList = gameRepository.findAll();
//    assertArrayEquals(savedGames.toArray(), gamesList.toArray());
    assertEquals(3, gamesList.size());
  }

  @Test
  public void serviceShouldSaveGetDeleteGame() {
    GameDto gameToSave = new GameDto();
    gameToSave.setName("Save Game Test");
    GameDto savedGame = gameService.saveGame(gameToSave);
    assertEquals(gameToSave.getName(), savedGame.getName());

    GameDto retrievedGame = gameService.getGameById(savedGame.getId());
    assertEquals(savedGame.getName(), retrievedGame.getName());

    assertTrue(gameService.deleteGame(savedGame.getId()));

    Assertions.assertThrows(EntityNotFoundException.class, () -> {
      gameService.getGameById(savedGame.getId());
    });
  }

  @Test
  public void getAllGamesUnauthorizedStatusForbidden() throws Exception {
    mockMvc.perform(get("/api/v1/games")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser("Blob")
  public void getAllGamesAuthorizedStatusOk() throws Exception {
    mockMvc.perform(get("/api/v1/games")).andExpect(status().isOk());
  }
}
