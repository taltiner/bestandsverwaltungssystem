package com.example.bestandservice.dto.request;

import com.example.bestandservice.model.Kategorie;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BestandRequestDTO {
    @Enumerated(EnumType.STRING)
    private Kategorie kategorie;
    private String name;
    private double preis;
    private Integer gesamtMenge;
    private Integer mindestMenge;
    private String detailInfo;
}
