package com.example.bestandservice.control;

import com.example.bestandservice.dto.request.BestandRequestDTO;
import com.example.bestandservice.model.Kategorie;
import com.example.bestandservice.service.BestandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BestandController.class)
public class BestandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BestandService bestandService;
    @Autowired
    private ObjectMapper objectMapper;
    private BestandRequestDTO bestandDTO;

    @BeforeEach
    void setUp() {
         bestandDTO = BestandRequestDTO.builder()
                .kategorie(Kategorie.HAUSHALT)
                .detailInfo("Küchenmesser Edelstahl")
                .gesamtMenge(1)
                .mindestMenge(10)
                .name("Santoku")
                .preis(99.95)
                .build();
    }

    @Test
    public void testSetBestand_Success() throws Exception{
        mockMvc.perform(post("/bestand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bestandDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Bestand wurde erfolgreich erstellt."));
    }

}
