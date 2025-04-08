package com.example.bestellservice.dto.request;

import com.example.bestellservice.model.BestellPosition;
import com.example.bestellservice.model.Status;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestellungRequestDTO {
    private Long id;
    @NotNull
    private Status status;
    @NotEmpty
    private List<BestellPosition> positionen = new ArrayList<>();
}
