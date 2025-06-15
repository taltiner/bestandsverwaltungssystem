package com.example.bestandservice.dto.response;

import com.example.bestandservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestellStatusResponseDTO {
    private Long bestellId;
    private Status status;
}
