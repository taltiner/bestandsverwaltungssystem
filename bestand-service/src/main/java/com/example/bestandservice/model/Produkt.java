package com.example.bestandservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Getter
@Setter
@Entity(name = "Produkt")
@NoArgsConstructor
@AllArgsConstructor
public class Produkt {
    @Id
    @SequenceGenerator(
            name = "produkt_sequence",
            sequenceName = "produkt_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "produkt_sequence"
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    private Kategorie kategorie;
    private String name;
    private double preis;
    @Column(name = "gesamtMenge")
    private Integer gesamtMenge;
    @Column(name = "detailInfo")
    private String detailInfo;

    public Produkt(
            Kategorie kategorie,
            String name,
            double preis,
            Integer gesamtMenge,
            String detailInfo) {
        this.kategorie = kategorie;
        this.name = name;
        this.preis = preis;
        this.gesamtMenge = gesamtMenge;
        this.detailInfo = detailInfo;
    }

}
