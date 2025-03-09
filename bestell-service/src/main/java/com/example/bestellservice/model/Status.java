package com.example.bestellservice.model;

public enum Status {
    ERFOLGREICH("erfolgreich"),
    NICHTERFOLGT("nichterfolgt");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
