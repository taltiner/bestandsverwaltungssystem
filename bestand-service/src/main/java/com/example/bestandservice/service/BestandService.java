package com.example.bestandservice.service;

import com.example.bestandservice.dto.request.BestandRequestDTO;
import com.example.bestandservice.dto.request.BestellungRequestDTO;
import com.example.bestandservice.dto.request.NachbestellRequestDTO;
import com.example.bestandservice.dto.response.NachbestellResponseDTO;
import com.example.bestandservice.exception.BestandPruefungException;
import com.example.bestandservice.exception.JsonMappingFailedException;
import com.example.bestandservice.exception.KafkaSendException;
import com.example.bestandservice.exception.ProduktNichtGefundenException;
import com.example.bestandservice.model.Produkt;
import com.example.bestandservice.model.Status;
import com.example.bestandservice.repository.ProduktRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class BestandService {

    private final ProduktRepository produktRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(BestandService.class);


    public void saveProdukt(BestandRequestDTO produkt) {
        Produkt savedProdukt = Produkt.builder()
                        .kategorie(produkt.getKategorie())
                        .name(produkt.getName())
                        .preis(produkt.getPreis())
                        .gesamtMenge(produkt.getGesamtMenge())
                        .mindestMenge(produkt.getMindestMenge())
                        .detailInfo(produkt.getDetailInfo())
                        .build();

        produktRepository.save(savedProdukt);
    }

    public void updateProdukt(Long id, Produkt produkt) {
        Produkt vorhandenesProdukt = produktRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produkt mit ID " + id + " nicht gefunden"));

        vorhandenesProdukt.setKategorie(produkt.getKategorie());
        vorhandenesProdukt.setName(produkt.getName());
        vorhandenesProdukt.setPreis(produkt.getPreis());
        vorhandenesProdukt.setDetailInfo(produkt.getDetailInfo());
        vorhandenesProdukt.setGesamtMenge(produkt.getGesamtMenge());

        produktRepository.save(vorhandenesProdukt);
    }

    public void updateBestandMenge(Long id, Integer menge) throws Exception {
        try {
            produktRepository.updateBestandMenge(id, menge);
        } catch (ProduktNichtGefundenException e) {
            throw new ProduktNichtGefundenException(id);
        }
    }

    public void pruefeObMindestBestandErreicht(String bestellungNachricht) {
        BestellungRequestDTO bestellungRequestDTO;
        try {
             bestellungRequestDTO = objectMapper.readValue(
                    bestellungNachricht,
                    BestellungRequestDTO.class);
        } catch(JsonProcessingException e) {
            throw new JsonMappingFailedException("Ungültiges JSON", e);
        }

        bestellungRequestDTO.getPositionen().forEach(position -> {
            try {
                Long id = position.getProduktId();
                Integer bestellMenge = position.getMenge();
                Produkt produktBestand = produktRepository.findById(id)
                    .orElseThrow(() -> new ProduktNichtGefundenException(id));

                Integer bestandMenge = produktBestand.getGesamtMenge();
                Integer mindestMenge = produktBestand.getMindestMenge();
                Integer maxMenge = produktBestand.getMaxMenge();
                Integer restMenge = bestandMenge - bestellMenge;

            if (restMenge <= mindestMenge) {
                log.warn("Mindestbestand für das Produkt mit der Id " + position.getProduktId() + " wurde unterschritten. Hieraus ergäbe sich eine Restmenge von: " + restMenge);
                Integer nachbestellMenge = maxMenge - restMenge;
                NachbestellResponseDTO nachbestellResponseDTO = new NachbestellResponseDTO(id,nachbestellMenge);
                bestellungRequestDTO.setStatus(Status.NICHTERFOLGT);
                sendeKafkaNachricht(nachbestellResponseDTO);
            } else {
                updateBestandMenge(id, restMenge);
                bestellungRequestDTO.setStatus(Status.ERFOLGREICH);
            }
            sendeKafkaNachricht(bestellungRequestDTO);

            } catch (Exception e) {
                throw new BestandPruefungException("Fehler beim Überprüfen des Bestands", e);
            }
        });
    }

    private void sendeKafkaNachricht(BestellungRequestDTO bestellungRequestDTO) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(bestellungRequestDTO);
            kafkaTemplate.send("bestellstatus", jsonMessage);
            log.info("Bestellstatus wurde erfolgreich an Kafka gesendet.");
        } catch (JsonProcessingException e) {
            log.error("Fehler beim Serialisieren der Bestellung: ", e);
            throw new KafkaSendException("Fehler beim Senden der Kafka-Nachricht", e);
        }
    }

    private void sendeKafkaNachricht(NachbestellResponseDTO nachbestellResponseDTO) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(nachbestellResponseDTO);
            kafkaTemplate.send("nachbestellung", jsonMessage);
            log.info("Nachbestellung wurde erfolgreich an Kafka gesendet.");
        } catch (JsonProcessingException e) {
            log.error("Fehler beim Serialisieren der Bestellung: ", e);
            throw new KafkaSendException("Fehler beim Senden der Kafka-Nachricht", e);
        }
    }

    public void setMaxMenge(String nachbestellNachricht) throws Exception {
        NachbestellRequestDTO nachbestellRequestDTO;
        try {
             nachbestellRequestDTO = objectMapper.readValue(
                    nachbestellNachricht,
                    NachbestellRequestDTO.class
            );
        } catch (JsonProcessingException e) {
            throw new JsonMappingFailedException("Ungültiges JSON", e);
        }
        Long produktId = nachbestellRequestDTO.getProduktId();
        Produkt produkt = produktRepository.findById(produktId)
                .orElseThrow(() -> new ProduktNichtGefundenException(produktId));
        Integer maxMenge = produkt.getMaxMenge();

        updateBestandMenge(produktId, maxMenge);
    }

}
