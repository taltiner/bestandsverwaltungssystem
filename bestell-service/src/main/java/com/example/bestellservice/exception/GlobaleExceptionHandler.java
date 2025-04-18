package com.example.bestellservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobaleExceptionHandler {

    @ExceptionHandler(UngueltigeBestellungException.class)
    public ResponseEntity<String> handleUngueltigeBestellungException(UngueltigeBestellungException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(KafkaSendException.class)
    public ResponseEntity<String> handleKafkaSendException(KafkaSendException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
