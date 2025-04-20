package com.example.bestellservice.exception;

import com.example.bestellservice.service.BestellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobaleExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BestellService.class);


    @ExceptionHandler(UngueltigeBestellungException.class)
    public ResponseEntity<String> handleUngueltigeBestellungException(UngueltigeBestellungException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(KafkaSendException.class)
    public ResponseEntity<String> handleKafkaSendException(KafkaSendException e) {
        log.error("KafkaSendException aufgetreten: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Die Bestellung konnte derzeit nicht abgeschlossen werden. Bitte versuchen Sie es später erneut.");
    }
}
