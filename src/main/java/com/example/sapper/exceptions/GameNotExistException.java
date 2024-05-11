package com.example.sapper.exceptions;

public class GameNotExistException extends RuntimeException{
    public GameNotExistException(String message) {
        super(message);
    }
}
