package com.example.sapper.service.impl;

import com.example.sapper.entity.Coordinate;
import com.example.sapper.entity.request.CreateGameRequest;
import com.example.sapper.exceptions.CountMinesException;
import com.example.sapper.exceptions.ExceptionFactory;
import com.example.sapper.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GenerateServiceImpl implements GeneratorService {
    private static final Random random = new Random();
    private final ExceptionFactory exceptionFactory;

    @Override
    public String generateGameId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Set<Coordinate> generateMineCoordinates(CreateGameRequest createGameRequest) {
        validateMinesCount(createGameRequest);
        HashSet<Coordinate> coordinateHashSet = new HashSet<>(createGameRequest.getMinesCount());
        do {
            Coordinate coordinate = new Coordinate(random.nextInt(createGameRequest.getHeight()), random.nextInt(createGameRequest.getWidth()));
            coordinateHashSet.add(coordinate);
        } while (!Objects.equals(coordinateHashSet.size(), createGameRequest.getMinesCount()));

        return coordinateHashSet;
    }

    private void validateMinesCount(CreateGameRequest createGameRequest) throws CountMinesException {
        if (createGameRequest.getMinesCount() > createGameRequest.getWidth() * createGameRequest.getHeight() - 1)
            exceptionFactory.newCountMinesException();
    }

}
