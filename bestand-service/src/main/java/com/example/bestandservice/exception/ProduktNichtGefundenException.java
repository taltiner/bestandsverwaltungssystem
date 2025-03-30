package com.example.bestandservice.exception;

public class ProduktNichtGefundenException extends RuntimeException {

    public ProduktNichtGefundenException(Long id) {
        super("Produkt mit der Id: " + id + " konnte nicht gefunden werden.");
    }
}
