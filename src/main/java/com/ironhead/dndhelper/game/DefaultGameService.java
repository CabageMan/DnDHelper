package com.ironhead.dndhelper.game;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("gameService")
public class DefaultGameService implements GameService {

  @Autowired
  private GameRepository gameRepository;

  /**
   * Create a Game based on the data sent to the service class.
   *
   * @param game GameDto
   * @return DTO representation of the game
   */
  @Override
  public GameDto saveGame(GameDto game) {
    Game gameEntity = populateGameEntity(game);
    return populateGameData(gameRepository.save(gameEntity));
  }

  /**
   * Delete game based on the ID.
   * Also, it's possible to delete game based on the entity
   * (passing JPA entity class as method parameter. But it also returns void, and we need return boolean. Check this moment)
   * @param gameId Long
   * @return boolean
   */
  @Override
  public boolean deleteGame(final Long gameId) {
    // Strange solution. May be to check for existing before deleting.
    gameRepository.deleteById(gameId);
    return true;
  }

  /**
   * Method to return the list of all the games in the system.
   * Improve this method to use pagination (it's important).
   * @return List<GameDto>
   */
  @Override
  public List<GameDto> getAllGames() {
    List<GameDto> gameDtoList = new ArrayList<>();
    List<Game> gamesList = gameRepository.findAll();
    gamesList.forEach(party -> { gameDtoList.add(populateGameData(party)); });
    return gameDtoList;
  }

  /**
   * Get game by ID.
   * @param gameId Long
   * @return GameDto
   */
  @Override
  public GameDto getGameById(final Long gameId) {
    return populateGameData(
            gameRepository
                    .findById(gameId)
                    .orElseThrow(() -> new EntityNotFoundException("Game not found"))
    );
  }

  /**
   * Internal method to map the front end Game object to the JPA Game entity.
   *
   * @param gameDto GameDto
   * @return Game
   */
  private Game populateGameEntity(GameDto gameDto) {
    Game game = new Game();
    // Use constructor instead setter
    game.setName(gameDto.getName());
    game.setCreatedTime(LocalDateTime.now());
    game.setUpdatedTime(LocalDateTime.now());
    return game;
  }

  /**
   * Internal method to convert Game JPA entity to the Data Transfer Object.
   *
   * @param game: Game
   * @return GameDto
   */
  private GameDto populateGameData(final Game game) {
    GameDto gameDto = new GameDto();
    gameDto.setId(game.getId());
    gameDto.setName(game.getName());
    return gameDto;
  }
}
