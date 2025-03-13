package com.example.bestellservice.dto.request;

import com.example.bestellservice.model.BestellPosition;
import com.example.bestellservice.model.Status;
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
    private List<BestellPosition> positionen = new ArrayList<>();
}
