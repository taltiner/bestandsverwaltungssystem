package com.example.bestandservice.service;

import com.example.bestandservice.dto.request.BestandRequestDTO;
import com.example.bestandservice.dto.request.BestellungRequestDTO;
import com.example.bestandservice.model.BestellPosition;
import com.example.bestandservice.model.Kategorie;
import com.example.bestandservice.model.Produkt;
import com.example.bestandservice.model.Status;
import com.example.bestandservice.repository.ProduktRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class bestandServiceTest {

    @InjectMocks
    private BestandService bestandService;
    @Mock
    private ProduktRepository produktRepository;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;
    private BestandRequestDTO bestandDTO;
    private Produkt produkt;
    private BestellungRequestDTO bestellungRequestDTO;

    @BeforeEach
    void setUp() {
        produkt = createProdukt(1);
        bestandDTO = createBestandDTO();
        bestellungRequestDTO = createBestellungDTO();
    }

    @Test
    public void testSaveProdukt_Success() {
        //when
        when(produktRepository.save(any(Produkt.class))).thenReturn(produkt);
        //then
        bestandService.saveProdukt(bestandDTO);
        //assert
        verify(produktRepository, times(1)).save(any(Produkt.class));
    }

    @Test
    public void testUpdateProdukt_Success() throws Exception {
        //when
        when(produktRepository.findById(any())).thenReturn(Optional.of(produkt));
        when(produktRepository.save(any(Produkt.class))).thenReturn(produkt);
        //then
        bestandService.updateProdukt(1L, produkt);
        //assert
        verify(produktRepository, times(1)).findById(any());
        verify(produktRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateProdukt_ProduktNichtGefunden() throws Exception {
        //when
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> bestandService.updateProdukt(1L, produkt)
        );
        //then & assert
        assertEquals("Produkt mit ID 1 nicht gefunden", exception.getMessage());
    }

    @Test
    public void testPruefeMindestbestand_Unterschritten() throws Exception {
        //when
        String json = objectMapper.writeValueAsString(bestellungRequestDTO);
        when(objectMapper.readValue(eq(json), eq(BestellungRequestDTO.class)))
                .thenReturn(bestellungRequestDTO);
        when(produktRepository.findById(any())).thenReturn(Optional.of(produkt));
        //then
        bestandService.pruefeObMindestBestandErreicht(json);
        //assert
        verify(kafkaTemplate, times(1)).send(eq("bestellstatus"), any());
        assertEquals(Status.NICHTERFOLGT, bestellungRequestDTO.getStatus());
    }

    @Test
    public void testPruefeMindestbestand_Grenzwert() throws Exception {
        //when
        Produkt produktNeu = createProdukt(15);
        String json = objectMapper.writeValueAsString(bestellungRequestDTO);
        when(objectMapper.readValue(eq(json), eq(BestellungRequestDTO.class)))
                .thenReturn(bestellungRequestDTO);
        when(produktRepository.findById(any())).thenReturn(Optional.of(produktNeu));
        //then
        bestandService.pruefeObMindestBestandErreicht(json);
        //assert
        verify(kafkaTemplate, times(1)).send(eq("bestellstatus"), any());
        assertEquals(Status.NICHTERFOLGT, bestellungRequestDTO.getStatus());
    }

    @Test
    public void testPruefeMindestbestand_NichtUnterschritten() throws Exception {
        //when
        Produkt produktNeu = createProdukt(16);
        String json = objectMapper.writeValueAsString(bestellungRequestDTO);
        when(objectMapper.readValue(eq(json), eq(BestellungRequestDTO.class)))
                .thenReturn(bestellungRequestDTO);
        when(produktRepository.findById(any())).thenReturn(Optional.of(produktNeu));
        doNothing().when(produktRepository).updateBestandMenge(any(Long.class), any(Integer.class));
        //then
        bestandService.pruefeObMindestBestandErreicht(json);
        //assert
        verify(kafkaTemplate, times(1)).send(eq("bestellstatus"), any());
        assertEquals(Status.ERFOLGREICH, bestellungRequestDTO.getStatus());
        verify(produktRepository).updateBestandMenge(any(Long.class), eq(11));
    }

    private Produkt createProdukt(Integer gesamtMenge) {
        return Produkt.builder()
                .kategorie(Kategorie.HAUSHALT)
                .detailInfo("Küchenmesser Edelstahl")
                .gesamtMenge(gesamtMenge)
                .mindestMenge(10)
                .name("Santoku")
                .preis(99.95)
                .build();
    }

    private BestandRequestDTO createBestandDTO() {
        return BestandRequestDTO.builder()
                .kategorie(Kategorie.HAUSHALT)
                .detailInfo("Küchenmesser Edelstahl")
                .gesamtMenge(1)
                .mindestMenge(10)
                .name("Santoku")
                .preis(99.95)
                .build();
    }

    private BestellungRequestDTO createBestellungDTO() {
        System.out.println("ENTERED");
        BestellPosition position = new BestellPosition();
        position.setMenge(5);
        position.setProduktId(1L);
        List<BestellPosition> positionen = new ArrayList<>();
        positionen.add(position);

        return bestellungRequestDTO.builder()
                .positionen(positionen)
                .status(Status.INPRUEFUNG)
                .build();
    }
}
