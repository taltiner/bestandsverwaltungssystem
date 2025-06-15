package com.example.benachrichtigungservice.control;

import com.example.benachrichtigungservice.dto.BestellStatusDTO;
import com.example.benachrichtigungservice.service.BenachrichtigungService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/bestellStatus")
@Validated
public class BenachrichtigungController {

    private final BenachrichtigungService benachrichtigungService;

    public BenachrichtigungController(BenachrichtigungService benachrichtigungService) {
        this.benachrichtigungService = benachrichtigungService;
    }

    @GetMapping
    public ResponseEntity<BestellStatusDTO> getBestellStatus(Long id) {
        BestellStatusDTO bestellStatusDTO = benachrichtigungService.getBestellStatus(id);

        return new ResponseEntity<>(bestellStatusDTO, HttpStatus.OK);
    }
}
