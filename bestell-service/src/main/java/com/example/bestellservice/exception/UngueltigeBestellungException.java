package com.example.bestellservice.exception;

public class UngueltigeBestellungException extends RuntimeException{

    public UngueltigeBestellungException(String message) {
        super(message);
    }
}
