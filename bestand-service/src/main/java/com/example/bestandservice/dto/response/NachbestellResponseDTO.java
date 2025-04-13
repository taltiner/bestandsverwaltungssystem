package com.example.bestandservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NachbestellResponseDTO {
    private Long produktId;
    private Integer nachbestellMenge;
}
