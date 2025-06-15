package com.example.bestellservice.service;

import com.example.bestellservice.dto.request.BestellungRequestDTO;
import com.example.bestellservice.exception.KafkaSendException;
import com.example.bestellservice.exception.UngueltigeBestellungException;
import com.example.bestellservice.model.BestellPosition;
import com.example.bestellservice.model.Bestellung;
import com.example.bestellservice.repository.BestellungRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.ProducerTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Service
public class BestellService {

    private final BestellungRepository bestellungRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProducerTemplate producerTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(BestellService.class);

    public BestellService(BestellungRepository bestellungRepository,
                          KafkaTemplate kafkaTemplate,
                          ProducerTemplate producerTemplate,
                          ObjectMapper objectMapper) {
        this.bestellungRepository = bestellungRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.producerTemplate = producerTemplate;
        this.objectMapper = objectMapper;
    }

    public void saveBestellung(BestellungRequestDTO bestellungRequestDTO) {
        if(bestellungRequestDTO.getPositionen().isEmpty()) {
            throw new UngueltigeBestellungException("Bestellung muss mindestens eine Bestellposition enthalten");
        }

        Bestellung bestellung = Bestellung.builder()
                        .status(bestellungRequestDTO.getStatus())
                        .positionen(new ArrayList<>())
                        .build();
        for(BestellPosition position : bestellungRequestDTO.getPositionen()) {
            position.setBestellung(bestellung);
            bestellung.getPositionen().add(position);
        }

        Bestellung savedBestellung = bestellungRepository.save(bestellung);

        bestellungRequestDTO.setId(savedBestellung.getId());
        for(int i = 0; i < savedBestellung.getPositionen().size(); i++) {
            Long bestellPositionId = savedBestellung.getPositionen().get(i).getId();
            bestellungRequestDTO.getPositionen().get(i).setId(bestellPositionId);
        }

        producerTemplate.sendBody("direct:sendBestellung", bestellungRequestDTO);

        //Deprecated durch Apache Camel
/*        try {
            String jsonMessage = objectMapper.writeValueAsString(bestellungRequestDTO);
            kafkaTemplate.send("bestellung", jsonMessage);
            log.info("Bestellstatus wurde erfolgreich an Kafka gesendet.");
        } catch (JsonProcessingException e) {
            log.error("Fehler beim Serialisieren der Bestellung: ", e);
            throw new KafkaSendException("Fehler beim Senden der Kafka-Nachricht", e);
        }*/

    }
}
