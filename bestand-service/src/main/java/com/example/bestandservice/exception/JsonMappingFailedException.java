package com.example.bestandservice.exception;

public class JsonMappingFailedException extends RuntimeException {

    public JsonMappingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
