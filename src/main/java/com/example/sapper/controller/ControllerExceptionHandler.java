package com.example.sapper.controller;

import com.example.sapper.exceptions.CountMinesException;
import com.example.sapper.exceptions.DoubleClickCellsException;
import com.example.sapper.exceptions.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {CountMinesException.class, NullPointerException.class, DoubleClickCellsException.class, Exception.class})
    public ResponseEntity<ResponseError> handle(Exception e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseError(e.getMessage()));
    }
}
