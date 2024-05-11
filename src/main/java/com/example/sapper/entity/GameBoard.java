package com.example.sapper.entity;

import com.example.sapper.constant.CellSymbols;
import com.example.sapper.entity.request.CreateGameRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class GameBoard {
    private List<List<String>> visibleGameBoard;
    private final List<List<String>> internalGameBoard;
    private final int width;
    private final int height;
    private final int minesCount;
    private int residueCells;

    public GameBoard(Set<Coordinate> mines, CreateGameRequest createGameRequest) {
        this.width = createGameRequest.getWidth();
        this.height = createGameRequest.getHeight();
        this.minesCount = createGameRequest.getMinesCount();
        this.visibleGameBoard = generateStartBoard();
        this.internalGameBoard = generateStartBoard();
        this.residueCells = width * height;
        fillInternalBoard(mines);
    }

    public void setCell(Type type, int row, int col, String value) {
        try {
            List<String> rowBoard = Type.VISIBLE.equals(type) ? visibleGameBoard.get(row) : internalGameBoard.get(row);
            rowBoard.set(col, value);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Непредвиденная ошибка");
        }
    }

    public Optional<String> getCell(Type type, int row, int col) {
        try {
            List<String> rowBoard = Type.VISIBLE.equals(type) ? visibleGameBoard.get(row) : internalGameBoard.get(row);
            return Optional.of(rowBoard.get(col));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private List<List<String>> generateStartBoard() {
        List<String> col = new ArrayList<>();
        for (int i = 0; i < this.width; i++) {
            col.add(CellSymbols.NOT_OPENED.getValue());
        }
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < this.height; i++) {
            rows.add(new ArrayList<>(col));
        }
        return rows;
    }

    private void fillInternalBoard(Set<Coordinate> mines) {
        mines.forEach(c -> this.setCell(Type.INTERNAL, c.row(), c.col(), CellSymbols.MINE.getValue()));
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                this.setCell(Type.INTERNAL, row, col, calcMines(row, col));
            }
        }
    }

    private String calcMines(int row, int col) {
        Optional<String> thisCell = this.getCell(Type.INTERNAL, row, col);
        if (thisCell.isPresent() && CellSymbols.MINE.getValue().equals(thisCell.get()))
            return thisCell.get();
        int countMines = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                Optional<String> cell = this.getCell(Type.INTERNAL, i, j);
                if (cell.isPresent() && CellSymbols.MINE.getValue().equals(cell.get()))
                    countMines++;
            }
        }
        return String.valueOf(countMines);
    }

    public enum Type {
        INTERNAL,
        VISIBLE;
    }

    public void decrementResidueCells() {
        this.residueCells--;
    }
}
