package com.example.bestandservice.service;

import com.example.bestandservice.dto.request.BestandRequestDTO;
import com.example.bestandservice.dto.request.BestellungRequestDTO;
import com.example.bestandservice.exception.BestandPruefungException;
import com.example.bestandservice.exception.KafkaSendException;
import com.example.bestandservice.exception.ProduktNichtGefundenException;
import com.example.bestandservice.model.Produkt;
import com.example.bestandservice.model.Status;
import com.example.bestandservice.repository.ProduktRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    public void updateBestandMenge(Long id, Integer menge) {
        produktRepository.updateBestandMenge(id, menge);
    }

    public void pruefeObMindestBestandErreicht(String bestellungNachricht) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BestellungRequestDTO bestellungRequestDTO = objectMapper.readValue(
                    bestellungNachricht,
                    BestellungRequestDTO.class);
            bestellungRequestDTO.getPositionen().forEach(position -> {
                Long id = position.getProduktId();
                Integer bestellMenge = position.getMenge();
                Produkt produktBestand = produktRepository.findById(id)
                    .orElseThrow(() -> new ProduktNichtGefundenException(id));
                Integer bestandMenge = produktBestand.getGesamtMenge();
                Integer mindestMenge = produktBestand.getMindestMenge();
                Integer restMenge = bestandMenge - bestellMenge;

            if (restMenge <= mindestMenge) {
                log.warn("Mindestbestand für das Produkt mit der Id " + position.getProduktId() + " wurde unterschritten. Hieraus ergäbe sich eine Restmenge von: " + restMenge);
                bestellungRequestDTO.setStatus(Status.NICHTERFOLGT);
            } else {
                updateBestandMenge(id, restMenge);
                bestellungRequestDTO.setStatus(Status.ERFOLGREICH);
            }
            sendeKafkaNachricht(bestellungRequestDTO);

            });
        } catch (Exception e) {
            throw new BestandPruefungException("Fehler beim Überprüfen des Bestands", e);
        }
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
}
