package com.aunnait.appmusic.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleInvalidDataException(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Other exceptions handling
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(JsonParseException.class)
//    public ResponseEntity<String> handleJsonParseException(JsonParseException ex, WebRequest request){
//        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(JsonPatchException.class)
//    public ResponseEntity<String> handleJsonPatchException(JsonPatchException ex, WebRequest request){
//        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}

