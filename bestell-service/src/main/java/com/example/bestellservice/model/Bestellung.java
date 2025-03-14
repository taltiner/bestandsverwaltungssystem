package com.example.bestellservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity(name = "bestellung")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @JsonManagedReference
    private List<BestellPosition> positionen = new ArrayList<>();
    public Bestellung(
            List<BestellPosition> positionen,
            Status status
    ) {
        this.positionen = positionen;
        this.status = status;
    }

}
