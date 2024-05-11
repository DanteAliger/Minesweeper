package com.example.sapper.entity;

import com.example.sapper.constant.CellSymbols;
import com.example.sapper.entity.request.CreateGameRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.Set;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game {

    @EqualsAndHashCode.Include
    private final String gameId;
    private final GameBoard gameBoard;
    private boolean isCompleted;

    public Game(String gameId, Set<Coordinate> mines, CreateGameRequest createGameRequest){
        this.gameId = gameId;
        this.gameBoard = new GameBoard(mines, createGameRequest);
        this.isCompleted = false;
    }

    public boolean isVictory(){
        if (this.getGameBoard().getMinesCount() == this.getGameBoard().getResidueCells()) {
            completeGame(true);
            return true;
        }
        return false;
    }

    public void completeGame(boolean isVictory) {
        this.getGameBoard().setVisibleGameBoard(this.getGameBoard().getInternalGameBoard());
        if (!isVictory){
            for (int row = 0; row < this.getGameBoard().getHeight(); row++) {
                for (int col = 0; col < this.getGameBoard().getWidth(); col++) {
                    Optional<String> cell = this.getGameBoard().getCell(GameBoard.Type.VISIBLE, row, col);
                    if (cell.isPresent() && CellSymbols.MINE.getValue().equals(cell.get()))
                        this.getGameBoard().setCell(GameBoard.Type.VISIBLE, row, col, CellSymbols.MINE_SYMBOL_FOR_SHOW.getValue());
                }
            }
        }
        this.isCompleted = true;
    }


}
