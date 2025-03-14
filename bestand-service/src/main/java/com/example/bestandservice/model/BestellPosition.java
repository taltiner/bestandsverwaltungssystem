package com.example.bestandservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BestellPosition {
    private Long id;
    private Long produktId;
    private Integer menge;
}
