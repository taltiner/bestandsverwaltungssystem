package com.example.bestellservice.control;

import com.example.bestellservice.dto.request.BestellungRequestDTO;
import com.example.bestellservice.service.BestellService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/bestellung")
public class BestellungController {

    private final BestellService bestellService;

    public BestellungController(BestellService bestellService) {
        this.bestellService = bestellService;
    }

    @PostMapping
    public ResponseEntity<String> erzeugeBestellung(@RequestBody BestellungRequestDTO bestellungRequestDTO) {
        bestellService.saveBestellung(bestellungRequestDTO);
        return ResponseEntity.ok("Bestellung erfolgreich erstellt und an Kafka gesendet.");

    }

}
