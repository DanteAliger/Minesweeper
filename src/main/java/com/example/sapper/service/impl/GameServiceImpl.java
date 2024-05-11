package com.example.sapper.service.impl;

import com.example.sapper.constant.CellSymbols;
import com.example.sapper.entity.Game;
import com.example.sapper.entity.GameBoard;
import com.example.sapper.entity.request.CreateGameRequest;
import com.example.sapper.entity.request.GameMoveRequest;
import com.example.sapper.entity.response.GameDto;
import com.example.sapper.exceptions.ExceptionFactory;
import com.example.sapper.repositoriy.GameRepository;
import com.example.sapper.service.GameService;
import com.example.sapper.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GeneratorService generatorService;
    private final GameRepository gameRepository;
    private final ExceptionFactory exceptionFactory;

    @Override
    public GameDto createGame(CreateGameRequest createGameRequest) {
        String gameId = generatorService.generateGameId();
        var coordinateHashSet = generatorService.generateMineCoordinates(createGameRequest);
        Game game = new Game(gameId, coordinateHashSet, createGameRequest);
        gameRepository.saveGame(gameId, game);

        return GameDto.builder()
                .gameId(gameId)
                .width(createGameRequest.getWidth())
                .height(createGameRequest.getHeight())
                .minesCount(createGameRequest.getMinesCount())
                .completed(game.isCompleted())
                .field(game.getGameBoard().getVisibleGameBoard())
                .build();
    }

    @Override
    public GameDto makeMove(GameMoveRequest request) {
        final Game game = gameRepository.getGame(request.getGameId());
        if (Objects.isNull(game))
            exceptionFactory.newGameNotExistException();
        makeMove(game, request.getRow(), request.getCol());
        game.isVictory();

        if(game.isCompleted())
            gameRepository.deleteGame(game.getGameId());

        return GameDto.builder()
                .gameId(game.getGameId())
                .width(game.getGameBoard().getWidth())
                .height(game.getGameBoard().getHeight())
                .minesCount(game.getGameBoard().getMinesCount())
                .completed(game.isCompleted())
                .field(game.getGameBoard().getVisibleGameBoard())
                .build();
    }

    private void makeMove(Game game, int row, int col) {
        var gameBoard = game.getGameBoard();
        Optional<String> visibleCell = gameBoard.getCell(GameBoard.Type.VISIBLE, row, col);
        visibleCell.ifPresentOrElse(value -> {
            if (!CellSymbols.NOT_OPENED.getValue().equals(value)) {
                exceptionFactory.newDoubleClickCellsException();
            }
        }, exceptionFactory::newCoordinateDoesNotExistException);

        Optional<String> internalCell = gameBoard.getCell(GameBoard.Type.INTERNAL, row, col);
        internalCell.ifPresentOrElse(value -> {
            if (CellSymbols.MINE.getValue().equals(value)) {
                game.completeGame(false);
            } else {
                if (!CellSymbols.ZERO.getValue().equals(value)) {
                    makeCellOpened(gameBoard, row, col, value);
                } else {
                    makeCellReview(game, row, col);
                }
            }
        }, exceptionFactory::newCoordinateDoesNotExistException);

    }

    private void makeCellOpened(GameBoard gameBoard, int row, int col, String value){
        gameBoard.setCell(GameBoard.Type.VISIBLE, row, col, value);
        gameBoard.decrementResidueCells();
    }

    private void makeCellReview(Game game, Integer row, Integer col) {
        GameBoard gameBoard = game.getGameBoard();
        Optional<String> internalCell = gameBoard.getCell(GameBoard.Type.INTERNAL, row, col);
        if (internalCell.isPresent()) {
            var valueCell = internalCell.get();
            if (CellSymbols.MINE.getValue().equals(valueCell)) {
                return;
            }
            makeCellOpened(gameBoard, row, col, valueCell);
            if (CellSymbols.ZERO.getValue().equals(valueCell)) {
                for (int i = row - 1; i <= row + 1; i += 1) {
                    for (int j = col - 1; j <= col + 1; j += 1) {
                        if (!(i == row && j == col) &&
                                CellSymbols.NOT_OPENED.getValue().equals(gameBoard.getCell(GameBoard.Type.VISIBLE, i, j).orElse(null)))
                        {
                                this.makeCellReview(game, i, j);
                        }
                    }
                }
            }
        }
    }

}
