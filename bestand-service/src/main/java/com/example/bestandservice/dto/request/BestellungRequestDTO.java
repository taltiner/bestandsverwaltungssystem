package com.example.bestandservice.dto.request;

import com.example.bestandservice.model.BestellPosition;
import com.example.bestandservice.model.Status;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestellungRequestDTO {
    private Long id;
    private Status status;
    List<BestellPosition> positionen = new ArrayList<>();
}
