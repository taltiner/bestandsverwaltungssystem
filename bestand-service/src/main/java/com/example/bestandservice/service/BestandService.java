package com.example.bestandservice.service;

import com.example.bestandservice.dto.request.BestellungRequestDTO;
import com.example.bestandservice.model.Produkt;
import com.example.bestandservice.repository.ProduktRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BestandService {

    private final ProduktRepository produktRepository;

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

            if(bestandMenge - bestellMenge <= 10) {
                System.out.println("Mindestbestand unterschritten" + (bestandMenge - bestellMenge));
            }
        });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
