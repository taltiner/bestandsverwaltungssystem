package com.example.benachrichtigungservice.service;

import com.example.benachrichtigungservice.dto.BestellStatusDTO;
import com.example.benachrichtigungservice.model.BestellStatus;
import com.example.benachrichtigungservice.repository.BenachrichtigungRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class BenachrichtigungService {

    private final BenachrichtigungRepository benachrichtigungRepository;

    public BenachrichtigungService(BenachrichtigungRepository benachrichtigungRepository,
                                   ObjectMapper objectMapper) {
        this.benachrichtigungRepository = benachrichtigungRepository;
    }

    public void updateBestellStatus(BestellStatusDTO bestellStatusDTO) {

        BestellStatus bestellStatus = BestellStatus.builder()
                .bestellId(bestellStatusDTO.getBestellId())
                .status(bestellStatusDTO.getStats())
                .build();

        benachrichtigungRepository.save(bestellStatus);
    }

    public BestellStatusDTO getBestellStatus(Long bestellId) {
        BestellStatus bestellStatus = benachrichtigungRepository.findByBestellId(bestellId);

        return BestellStatusDTO.builder()
                .bestellId(bestellStatus.getBestellId())
                .stats(bestellStatus.getStatus())
                .build();
    }
}
