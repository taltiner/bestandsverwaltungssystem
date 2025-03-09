package com.example.bestellservice.service;

import com.example.bestellservice.model.Bestellung;
import com.example.bestellservice.repository.BestellungRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class BestellService {

    private final BestellungRepository bestellungRepository;

    public BestellService(BestellungRepository bestellungRepository) {
        this.bestellungRepository = bestellungRepository;
    }

    public void saveBestellung(Bestellung bestellung) {
        bestellungRepository.save(bestellung);
    }
}
