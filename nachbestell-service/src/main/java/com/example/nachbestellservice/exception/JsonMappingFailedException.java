package com.example.nachbestellservice.exception;

public class JsonMappingFailedException extends RuntimeException{

    public JsonMappingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
