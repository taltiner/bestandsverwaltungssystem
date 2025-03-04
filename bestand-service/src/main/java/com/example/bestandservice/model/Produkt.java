package com.example.bestandservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "Produkt")
@NoArgsConstructor
@AllArgsConstructor
public class Produkt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Kategorie kategorie;
    private String name;
    private double preis;
    private Integer menge;
    private String detailInfo;

    public Produkt(
            Kategorie kategorie,
            String name,
            double preis,
            Integer menge,
            String detailInfo) {
        this.kategorie = kategorie;
        this.name = name;
        this.preis = preis;
        this.menge = menge;
        this.detailInfo = detailInfo;
    }

}
