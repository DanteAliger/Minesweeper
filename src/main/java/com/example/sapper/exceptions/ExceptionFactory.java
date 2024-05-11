package com.example.sapper.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ExceptionFactory {

    public void newCountMinesException() {
        throw new CountMinesException("Некорректное количество мин на поле");
    }

    public void newDoubleClickCellsException() {
        throw new DoubleClickCellsException("Ячейка нажата повторно");
    }

    public void newCoordinateDoesNotExistException() {
        throw new CoordinateNotExistException("Координаты за перелами поля");
    }

    public void newGameNotExistException() {
        throw new GameNotExistException("Игры не существует");
    }

}
