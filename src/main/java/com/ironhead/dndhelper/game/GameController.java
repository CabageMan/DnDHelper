package com.ironhead.dndhelper.game;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GameController {

  @Resource(name = "gameService")
  private GameService gameService;

  /**
   * <p>Get all games data. The pagination should be used here.</p>
   * @return List<GameDto>
   */
  @GetMapping(value = "/games")
  public List<GameDto> getGames() {
    return gameService.getAllGames();
  }

  /**
   * <p>Method to get the game data based on the ID.</p>
   * @param id: Long
   * @return GameDto
   */
  @GetMapping("/game/{id}")
  public GameDto getGame(@PathVariable Long id) {
    return gameService.getGameById(id);
  }

  /**
   * <p>Post request to create the Game object.</p>
   * @param gameDto GameDto
   * @return GameDta
   */
  @PostMapping("/save_game")
  public GameDto saveGame(final @RequestBody GameDto gameDto) {
    return gameService.saveGame(gameDto);
  }

  /**
   * <p>Delete game from the system based on the ID.</p>
   * @param id: Long
   * @return Boolean
   */
  @DeleteMapping("/game/{id}")
  public Boolean deleteGame(@PathVariable Long id) {
    return gameService.deleteGame(id);
  }
}
