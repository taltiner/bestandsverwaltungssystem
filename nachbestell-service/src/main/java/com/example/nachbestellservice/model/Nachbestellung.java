package com.example.nachbestellservice.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity(name = "Nachbestellung")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nachbestellung {
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
    @Column(name = "produkt_id")
    private Long produktId;
    private Integer nachbestellMenge;
}
