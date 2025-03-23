package com.example.bestandservice.control;

import com.example.bestandservice.dto.request.BestandRequestDTO;
import com.example.bestandservice.model.Produkt;
import com.example.bestandservice.service.BestandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/bestand")
@AllArgsConstructor
public class BestandController {

    private final BestandService bestandService;

    @PostMapping
    public ResponseEntity<String> setBestand(@RequestBody BestandRequestDTO produkt) {
        bestandService.saveProdukt(produkt);
        return ResponseEntity.ok("Bestand wurde erfolgreich erstellt.");
    }
}
