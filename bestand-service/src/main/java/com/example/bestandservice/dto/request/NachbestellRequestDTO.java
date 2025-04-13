package com.example.bestandservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NachbestellRequestDTO {
    private Long produktId;
    private Integer nachbestellMenge;
}
