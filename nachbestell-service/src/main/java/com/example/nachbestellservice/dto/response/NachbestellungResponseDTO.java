package com.example.nachbestellservice.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NachbestellungResponseDTO {
    private Long produktId;
    private Integer nachbestellMenge;
}
