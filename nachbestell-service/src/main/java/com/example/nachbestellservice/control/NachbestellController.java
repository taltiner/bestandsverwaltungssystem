package com.example.nachbestellservice.control;

import com.example.nachbestellservice.dto.request.NachbestellungRequestDTO;
import com.example.nachbestellservice.service.NachbestellService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/nachbestellung")
@AllArgsConstructor
public class NachbestellController {

    private final NachbestellService nachbestellService;

    @PostMapping
    public ResponseEntity<String> beendeNachbestellung(@RequestBody @Valid NachbestellungRequestDTO nachbestellungRequestDTO) {
        nachbestellService.deleteNachbestellung(nachbestellungRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Die Nachbestellung wurde erfolgreich beendet.");
    }
}
