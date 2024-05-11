package com.example.sapper.repositoriy;

import com.example.sapper.entity.Game;

public interface GameRepository {
    void saveGame(String gameId, Game gameBoards);

    Game getGame(String gameId);

    void deleteGame(String gameId);
}
