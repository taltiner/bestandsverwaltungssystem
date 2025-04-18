package com.example.nachbestellservice.control;

import com.example.nachbestellservice.dto.request.NachbestellungRequestDTO;
import com.example.nachbestellservice.exception.NachbestellungBereitsBeendetException;
import com.example.nachbestellservice.repository.NachbestellRepository;
import com.example.nachbestellservice.service.NachbestellService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NachbestellController.class)
public class NachbestellControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NachbestellService nachbestellService;
    @MockBean
    private NachbestellRepository nachbestellRepository;

    private NachbestellungRequestDTO nachbestellungRequestDTO;

    @BeforeEach
    public void setUp() {
        nachbestellungRequestDTO = NachbestellungRequestDTO.builder()
                .produktId(5L)
                .nachbestellMenge(10)
                .build();
    }

    @Test
    public void testBeendeNachbestellung_Success() throws Exception{
        //when
        doNothing().when(nachbestellRepository).deleteById(any(Long.class));
        //then & assert
        mockMvc.perform(post("/nachbestellung")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nachbestellungRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Die Nachbestellung wurde erfolgreich beendet."));
    }

    @Test
    public void testBeendeNachbestellung_Failure() throws Exception {
        //when
        doThrow(new NachbestellungBereitsBeendetException("Für dieses Produkt ist die Nachbestellung bereits beendet."))
                .when(nachbestellService)
                .deleteNachbestellung(any(NachbestellungRequestDTO.class));

        // when & then
        mockMvc.perform(post("/nachbestellung")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nachbestellungRequestDTO)))
                .andExpect(status().isBadRequest());
    }
}
