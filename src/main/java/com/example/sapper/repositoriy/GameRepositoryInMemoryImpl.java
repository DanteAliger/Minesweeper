package com.example.sapper.repositoriy;

import com.example.sapper.entity.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class GameRepositoryInMemoryImpl implements GameRepository {
    private final HashMap<String, Game> games = new HashMap<>();

    public void saveGame(String gameId, Game game) {
        games.put(gameId, game);
    }

    public Game getGame(String gameId) {
        return games.get(gameId);
    }

    public void deleteGame(String gameId) {
        games.remove(gameId);
    }
}
