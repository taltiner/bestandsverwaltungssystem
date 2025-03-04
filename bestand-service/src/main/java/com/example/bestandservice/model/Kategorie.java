package com.example.bestandservice.model;

public enum Kategorie {
    SMARTPHONE("smartphone"),
    KLEIDUNG("kleidung"),
    HAUSHALT("haushalt");

    private final String value;

    Kategorie(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
