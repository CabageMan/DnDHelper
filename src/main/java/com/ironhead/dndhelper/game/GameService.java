package com.ironhead.dndhelper.game;

import java.util.List;

public interface GameService {
  GameDto saveGame(GameDto game);
  boolean deleteGame(final Long gameId);
  List<GameDto> getAllGames();
  GameDto getGameById(final Long gameId);
}
