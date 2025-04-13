package com.example.nachbestellservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobaleExceptionHandler {

    @ExceptionHandler(JsonMappingFailedException.class)
    public ResponseEntity<String> handleJsonMappingFailedException(JsonMappingFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SaveNachbestellungException.class)
    public ResponseEntity<String> handleSaveNachbestellungException(SaveNachbestellungException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(DublicateNachbestellungException.class)
    public ResponseEntity<String> handleDublicateNachbestellungException(DublicateNachbestellungException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
