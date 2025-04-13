package com.example.nachbestellservice.exception;

public class NachbestellungBereitsBeendetException extends RuntimeException {

    public NachbestellungBereitsBeendetException(String message) {
        super(message);
    }
}
