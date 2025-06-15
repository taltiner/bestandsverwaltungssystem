package com.example.benachrichtigungservice.dto;

import com.example.benachrichtigungservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestellStatusDTO {
    private Long bestellId;
    private Status stats;
}
