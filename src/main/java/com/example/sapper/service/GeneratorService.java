package com.example.sapper.service;

import com.example.sapper.entity.Coordinate;
import com.example.sapper.entity.request.CreateGameRequest;

import java.util.Set;

public interface GeneratorService {

    String generateGameId();

    Set<Coordinate> generateMineCoordinates(CreateGameRequest createGameRequest);
}
