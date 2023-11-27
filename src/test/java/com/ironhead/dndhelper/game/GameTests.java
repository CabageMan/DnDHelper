package com.ironhead.dndhelper.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameTests {

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
    // Need learn how to test JPA entities
    Game firstSavedEntity = gameRepository.save(new Game("Test Find All Game 1"));
    Game secondSavedEntity = gameRepository.save(new Game("Test Find All Game 2"));
    Game thirdSavedEntity = gameRepository.save(new Game("Test Find All Game 3"));
    assertNotNull(firstSavedEntity);
    assertNotNull(secondSavedEntity);
    assertNotNull(thirdSavedEntity);

    List<Game> savedGames = new ArrayList<>();
    savedGames.add(firstSavedEntity);
    savedGames.add(secondSavedEntity);
    savedGames.add(thirdSavedEntity);

    List<Game> gamesList = gameRepository.findAll();
    assertArrayEquals(savedGames.toArray(), gamesList.toArray());
  }

  @Test
  public void serviceShouldSaveAndReturnSeveralGames() {
    GameDto firstGameToSave = new GameDto();
    firstGameToSave.setName("Save Game Test 1");
    assertNotNull(gameService.saveGame(firstGameToSave));
    GameDto secondGameToSave = new GameDto();
    firstGameToSave.setName("Save Game Test 2");
    assertNotNull(gameService.saveGame(secondGameToSave));
    GameDto thirdGameToSave = new GameDto();
    firstGameToSave.setName("Save Game Test 3");
    assertNotNull(gameService.saveGame(thirdGameToSave));

    List<GameDto> savedGames = gameService.getAllGames();
    assertEquals(3, savedGames.size());
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
    GameDto retrivedDeletedGame = gameService.getGameById(savedGame.getId());
    assertNull(retrivedDeletedGame);
  }
}
