package com.example.nachbestellservice.dto.request;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NachbestellungRequestDTO {
    private Long produktId;
    private Integer nachbestellMenge;
}

