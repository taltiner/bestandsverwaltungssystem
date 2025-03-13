package com.example.bestellservice.service;

import com.example.bestellservice.dto.request.BestellungRequestDTO;
import com.example.bestellservice.model.Bestellung;
import com.example.bestellservice.repository.BestellungRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BestellService {

    private final BestellungRepository bestellungRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public BestellService(BestellungRepository bestellungRepository,
                          KafkaTemplate kafkaTemplate) {
        this.bestellungRepository = bestellungRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void saveBestellung(BestellungRequestDTO bestellungRequestDTO) {
        Bestellung bestellung = Bestellung.builder()
                        .status(bestellungRequestDTO.getStatus())
                        .positionen(bestellungRequestDTO.getPositionen())
                        .build();
        bestellungRepository.save(bestellung);
        bestellungRequestDTO.setId(bestellung.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonMessage = objectMapper.writeValueAsString(bestellung);
            kafkaTemplate.send("bestellung", jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
