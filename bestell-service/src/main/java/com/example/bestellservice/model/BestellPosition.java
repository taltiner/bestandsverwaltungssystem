package com.example.bestellservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BestellPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long produktId;
    private Integer Menge;
    @ManyToOne
    @JoinColumn(name = "bestellung_id")
    private Bestellung bestellung;

}
