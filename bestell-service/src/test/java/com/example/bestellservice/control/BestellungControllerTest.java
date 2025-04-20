package com.example.bestellservice.control;

import com.example.bestellservice.dto.request.BestellungRequestDTO;
import com.example.bestellservice.model.BestellPosition;
import com.example.bestellservice.model.Status;
import com.example.bestellservice.service.BestellService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BestellungController.class)
public class BestellungControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BestellService bestellService;

    @Test
    public void testErzeugeBestellung_Success() throws Exception {
        BestellungRequestDTO bestellungDTO = BestellungRequestDTO.builder()
                .status(Status.INPRUEFUNG)
                .positionen(List.of( new BestellPosition()))
                .build();

        BestellPosition position = bestellungDTO.getPositionen().get(0);
        position.setProduktId(1L);
        position.setMenge(5);

        mockMvc.perform(post("/bestellung")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bestellungDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Ihre Bestellung ist erfolgreich eingegangen und wird nun geprüft."));
    }

    @Test
    public void testErzeugeBestellung_Failure() throws Exception {
        BestellungRequestDTO bestellungDTO = new BestellungRequestDTO();
        bestellungDTO.setStatus(Status.INPRUEFUNG);
        bestellungDTO.setPositionen(new ArrayList<>());

        mockMvc.perform(post("/bestellung")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bestellungDTO)))
                .andExpect(status().isBadRequest());
    }
}
