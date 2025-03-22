package com.example.bestandservice.service;

import com.example.bestandservice.dto.request.BestellungRequestDTO;
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


    public void saveProdukt(Produkt produkt) {
        produktRepository.save(produkt);
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
                    .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden"));
            Integer bestandMenge = produktBestand.getGesamtMenge();
            Integer mindestMenge = produktBestand.getMindestMenge();

            if(bestandMenge - bestellMenge <= mindestMenge) {
                System.out.println("Mindestbestand unterschritten" + (bestandMenge - bestellMenge));
                try {
                    bestellungRequestDTO.setStatus(Status.NICHTERFOLGT);
                    String jsonMessage = objectMapper.writeValueAsString(bestellungRequestDTO);
                    kafkaTemplate.send("bestellstatus", jsonMessage);
                } catch (JsonProcessingException e) {
                    log.error("Fehler beim Serialisieren der Bestellung: ", e);
                }
            } else {
                try{
                    bestellungRequestDTO.setStatus(Status.ERFOLGREICH);
                    String jsonMessage = objectMapper.writeValueAsString(bestellungRequestDTO);
                    kafkaTemplate.send("bestellstatus", jsonMessage);
                }catch (JsonProcessingException e) {
                    log.error("Fehler beim Serialisieren der Bestellung: ", e);
                }

            }
        });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
