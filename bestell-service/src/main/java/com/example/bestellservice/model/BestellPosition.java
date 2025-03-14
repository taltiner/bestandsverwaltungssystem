package com.example.bestellservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Integer menge;
    @ManyToOne
    @JoinColumn(name = "bestellung_id")
    @JsonBackReference
    private Bestellung bestellung;

}
