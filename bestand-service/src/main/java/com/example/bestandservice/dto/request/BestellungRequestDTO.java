package com.example.bestandservice.dto.request;

import com.example.bestandservice.model.BestellPosition;
import com.example.bestandservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BestellungRequestDTO {
    private Long id;
    private Status status;
    List<BestellPosition> positionen = new ArrayList<>();
}
