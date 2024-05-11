package com.example.sapper.controller;

import com.example.sapper.entity.request.CreateGameRequest;
import com.example.sapper.entity.request.GameMoveRequest;
import com.example.sapper.entity.response.GameDto;
import com.example.sapper.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/minesweeper")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/new")
    public ResponseEntity<GameDto> create(@Valid @RequestBody CreateGameRequest request) {
        GameDto game = gameService.createGame(request);
        return ResponseEntity.status(HttpStatus.OK).body(game);
    }

    @PostMapping("/turn")
    public ResponseEntity<GameDto> move(@Valid @RequestBody GameMoveRequest request) {
        GameDto responseGame = gameService.makeMove(request);
        return ResponseEntity.status(HttpStatus.OK).body(responseGame);
    }
}
