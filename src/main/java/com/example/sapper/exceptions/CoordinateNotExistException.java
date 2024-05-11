package com.example.sapper.exceptions;

public class CoordinateNotExistException extends RuntimeException {
    public CoordinateNotExistException(String message) {
        super(message);
    }
}
