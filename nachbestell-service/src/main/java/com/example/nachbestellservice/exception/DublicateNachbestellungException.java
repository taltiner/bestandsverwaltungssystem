package com.example.nachbestellservice.exception;

public class DublicateNachbestellungException extends RuntimeException {

    public DublicateNachbestellungException(String message) {
        super(message);
    }
}
