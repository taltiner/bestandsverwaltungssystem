package com.example.bestandservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobaleExceptionHandler {

    @ExceptionHandler(BestandPruefungException.class)
    public ResponseEntity<String> handleBestandPruefungException(BestandPruefungException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ProduktNichtGefundenException.class)
    public ResponseEntity<String> handleProduktNichtGefundenException(ProduktNichtGefundenException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(KafkaSendException.class)
    public ResponseEntity<String> handleKafkaSendException(KafkaSendException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(JsonMappingFailedException.class)
    public ResponseEntity<String> handleJsonMappingFailedException(JsonMappingFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
