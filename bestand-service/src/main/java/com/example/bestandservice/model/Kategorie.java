package com.example.bestandservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Kategorie {
    SMARTPHONE("smartphone"),
    KLEIDUNG("kleidung"),
    HAUSHALT("haushalt");

    private final String value;

    Kategorie(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Kategorie fromValue(String value) {
        for (Kategorie kategorie : values()) {
            if (kategorie.value.equalsIgnoreCase(value)) {
                return kategorie;
            }
        }
        throw new IllegalArgumentException("Ungültige Kategorie: " + value);
    }
}
