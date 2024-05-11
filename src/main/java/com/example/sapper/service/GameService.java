package com.example.sapper.service;

import com.example.sapper.entity.request.CreateGameRequest;
import com.example.sapper.entity.request.GameMoveRequest;
import com.example.sapper.entity.response.GameDto;

public interface GameService {

    GameDto createGame(CreateGameRequest createGameRequest);
    GameDto makeMove(GameMoveRequest request);

}
