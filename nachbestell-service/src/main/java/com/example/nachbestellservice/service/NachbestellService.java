package com.example.nachbestellservice.service;

import com.example.nachbestellservice.dto.request.NachbestellungRequestDTO;
import com.example.nachbestellservice.dto.response.NachbestellungResponseDTO;
import com.example.nachbestellservice.exception.*;
import com.example.nachbestellservice.model.Nachbestellung;
import com.example.nachbestellservice.repository.NachbestellRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class NachbestellService {

    private final NachbestellRepository nachbestellRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProducerTemplate producerTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(NachbestellService.class);


    public void saveNachbestellung(NachbestellungRequestDTO nachbestellungRequestDTO) {
/*        NachbestellungRequestDTO nachbestellungRequestDTO;
        try {
             nachbestellungRequestDTO = objectMapper.readValue(
                    nachbestellNachricht,
                    NachbestellungRequestDTO.class) ;
            } catch(JsonProcessingException e) {
                throw new JsonMappingFailedException("Ungültiges JSON", e);
            }*/

        Nachbestellung nachbestellung = Nachbestellung.builder()
            .produktId(nachbestellungRequestDTO.getProduktId())
            .nachbestellMenge(nachbestellungRequestDTO.getNachbestellMenge())
            .build();

        if(nachbestellRepository.findByProduktId(nachbestellung.getProduktId()).isPresent()) {
            throw new DublicateNachbestellungException("Eine Nachbestellung für dieses Produkt ist bereits eingegangen");
        }

        try {
            nachbestellRepository.save(nachbestellung);
        } catch(Exception e) {
            throw new SaveNachbestellungException("Allgemeiner Fehler beim Speichern der Nachbestellung", e);
        }
    }

    public void deleteNachbestellung(NachbestellungRequestDTO nachbestellungRequestDTO) {
        Optional<Nachbestellung> nachbestellung = nachbestellRepository.findByProduktId(nachbestellungRequestDTO.getProduktId());
        if(nachbestellung.isPresent()) {
            nachbestellRepository.deleteById(nachbestellung.get().getId());
            producerTemplate.sendBody("direct:sendNachbestellungLoeschen", nachbestellungRequestDTO);
            //sendeKafkaNachricht(nachbestellungRequestDTO);
        } else {
            throw new NachbestellungBereitsBeendetException("Für dieses Produkt ist die Nachbestellung bereits beendet.");
        }
    }

    private void sendeKafkaNachricht(NachbestellungRequestDTO nachbestellungRequestDTO) {
        log.info("produktId: " + nachbestellungRequestDTO.getProduktId());
        try {
            NachbestellungResponseDTO nachbestellungResponseDTO = NachbestellungResponseDTO.builder()
                    .produktId(nachbestellungRequestDTO.getProduktId())
                    .nachbestellMenge(nachbestellungRequestDTO.getNachbestellMenge())
                    .build();

            String jsonMessage = objectMapper.writeValueAsString(nachbestellungResponseDTO);
            kafkaTemplate.send("nachbestellung-loeschen", jsonMessage);
            log.info("Nachbestellung wurde erfolgreich an Kafka gesendet.");
        } catch (JsonProcessingException e) {
            log.error("Fehler beim Serialisieren der Bestellung: ", e);
            throw new KafkaSendException("Fehler beim Senden der Kafka-Nachricht", e);
        }
    }
}
