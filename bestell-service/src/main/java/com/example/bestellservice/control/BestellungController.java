package com.example.bestellservice.control;

import com.example.bestellservice.dto.request.BestellungRequestDTO;
import com.example.bestellservice.service.BestellService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping(path = "/bestellung")
@Validated
public class BestellungController {

    private final BestellService bestellService;

    public BestellungController(BestellService bestellService) {
        this.bestellService = bestellService;
    }

    @PostMapping
    public ResponseEntity<String> erzeugeBestellung(@RequestBody @Valid BestellungRequestDTO bestellungRequestDTO) {
        bestellService.saveBestellung(bestellungRequestDTO);

        return ResponseEntity.ok("Ihre Bestellung ist erfolgreich eingegangen und wird nun geprüft.");
    }

}
