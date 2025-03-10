package com.example.bestellservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity(name = "bestellung")
@NoArgsConstructor
@AllArgsConstructor
public class Bestellung {
    @Id
    @SequenceGenerator(
            name = "bestell_sequence",
            sequenceName = "bestell_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "bestell_sequence"
    )
    private Long id;
    private Status status;

    @OneToMany(mappedBy = "bestellung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BestellPosition> positionen = new ArrayList<>();
    public Bestellung(
            List<BestellPosition> positionen,
            Status status
    ) {
        this.positionen = positionen;
        this.status = status;
    }

}
