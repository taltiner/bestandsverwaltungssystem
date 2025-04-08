package com.example.bestellservice.service;

import com.example.bestellservice.dto.request.BestellungRequestDTO;
import com.example.bestellservice.exception.KafkaSendException;
import com.example.bestellservice.exception.UngueltigeBestellungException;
import com.example.bestellservice.model.BestellPosition;
import com.example.bestellservice.model.Bestellung;
import com.example.bestellservice.model.Status;
import com.example.bestellservice.repository.BestellungRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BestellServiceTest {

    @Mock
    private BestellungRepository bestellungRepository;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private BestellService bestellService;
    private BestellungRequestDTO bestellungDTO;
    private Bestellung bestellung;

    @BeforeEach
    void setUp() {
        BestellPosition position = new BestellPosition();
         bestellungDTO = BestellungRequestDTO.builder()
                .status(Status.INPRUEFUNG)
                .positionen(List.of(position))
                .build();

        bestellung = Bestellung.builder()
                .status(Status.INPRUEFUNG)
                .positionen(List.of(position))
                .build();

        position.setProduktId(1L);
        position.setMenge(5);
    }

    @Test
    public void testSaveBestellung_Success() throws Exception {
        //when
        when(bestellungRepository.save(any(Bestellung.class))).thenReturn(bestellung);
        when(objectMapper.writeValueAsString(any())).thenReturn("JSON");
        //then
        bestellService.saveBestellung(bestellungDTO);
        //assert
        verify(bestellungRepository, times(1)).save(any(Bestellung.class));
    }

    @Test
    public void testSaveBestellung_mitLeerenPositionen_wirftException() throws Exception {
        //when
        bestellungDTO.setPositionen(new ArrayList<>());
        //then & assert
        UngueltigeBestellungException exception = assertThrows(
                UngueltigeBestellungException.class,
                () -> bestellService.saveBestellung(bestellungDTO)
        );
        assertEquals("Bestellung muss mindestens eine Bestellposition enthalten", exception.getMessage());
    }

    @Test
    public void testSaveBestellung_kafkaFailure() throws JsonProcessingException{
        //when
        when(bestellungRepository.save(any(Bestellung.class))).thenReturn(bestellung);
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Fehler!") {});
        //then & assert
        KafkaSendException exception = assertThrows(
                KafkaSendException.class,
                () -> bestellService.saveBestellung(bestellungDTO)
        );
        assertEquals("Fehler beim Senden der Kafka-Nachricht", exception.getMessage());
        verify(kafkaTemplate, never()).send(any(), any());
    }
}
