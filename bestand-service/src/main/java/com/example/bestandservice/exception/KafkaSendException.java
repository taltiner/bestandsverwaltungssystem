package com.example.bestandservice.exception;

public class KafkaSendException extends RuntimeException {

    public KafkaSendException(String message, Throwable cause) {
        super(message, cause);
    }

}
