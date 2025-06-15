package com.example.nachbestellservice.service;

import com.example.nachbestellservice.dto.request.NachbestellungRequestDTO;
import com.example.nachbestellservice.exception.DublicateNachbestellungException;
import com.example.nachbestellservice.exception.JsonMappingFailedException;
import com.example.nachbestellservice.exception.NachbestellungBereitsBeendetException;
import com.example.nachbestellservice.exception.SaveNachbestellungException;
import com.example.nachbestellservice.model.Nachbestellung;
import com.example.nachbestellservice.repository.NachbestellRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class nachbestellServiceTest {

    @InjectMocks
    private NachbestellService nachbestellService;

    @Mock
    private NachbestellRepository nachbestellRepository;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;

    private NachbestellungRequestDTO nachbestellungRequestDTO;
    private Nachbestellung nachbestellung;

    @BeforeEach
    void setUp() {
         nachbestellungRequestDTO = NachbestellungRequestDTO.builder()
                .produktId(1L)
                .nachbestellMenge(10)
                .build();

         nachbestellung = Nachbestellung.builder()
                 .id(2L)
                 .produktId(1L)
                 .nachbestellMenge(10)
                 .build();
    }

 /*    @Test
    public void testSaveNachbestellung_Success() throws Exception {
        //when
        String json = objectMapper.writeValueAsString(nachbestellungRequestDTO);
        when(objectMapper.readValue(eq(json), eq(NachbestellungRequestDTO.class)))
                .thenReturn(nachbestellungRequestDTO);
        when(nachbestellRepository.save(any(Nachbestellung.class))).thenReturn(nachbestellung);
        //then
        nachbestellService.saveNachbestellung(json);
        //assert
        verify(nachbestellRepository, times(1)).save(any(Nachbestellung.class));
    }

   @Test
    public void testSaveNachbestellung_Dublikat() throws Exception {
        //when
        String json = objectMapper.writeValueAsString(nachbestellungRequestDTO);
        when(objectMapper.readValue(eq(json), eq(NachbestellungRequestDTO.class)))
                .thenReturn(nachbestellungRequestDTO);
        when(nachbestellRepository.findByProduktId(1L)).thenReturn(Optional.of(nachbestellung));
        //then & assert
        assertThrows(DublicateNachbestellungException.class, () -> {
           nachbestellService.saveNachbestellung(json);
        });
        verify(nachbestellRepository, never()).save(any());
    }

    @Test
    public void testSaveNachbestellung_MappingFailure() throws Exception {
        //when
        when(objectMapper.readValue(anyString(), eq(NachbestellungRequestDTO.class)))
                .thenThrow(new JsonProcessingException("Fehler beim Parsen") {});
        //then & assert
        assertThrows(JsonMappingFailedException.class, () -> {
            nachbestellService.saveNachbestellung("json");
        });

        verify(nachbestellRepository, never()).save(any());
    }

    @Test
    public void testSaveNachbestellung_AllgemeinFailure() throws Exception {
        //when
        String json = objectMapper.writeValueAsString(nachbestellungRequestDTO);
        when(objectMapper.readValue(eq(json), eq(NachbestellungRequestDTO.class)))
                .thenReturn(nachbestellungRequestDTO);
        //then & assert
        doThrow(new RuntimeException("Datenbankfehler")).when(nachbestellRepository).save(any());

        assertThrows(SaveNachbestellungException.class, () -> {
            nachbestellService.saveNachbestellung(json);
        });
    }*/

/*    @Test
    public void testDeleteNachbestellung_Success() throws Exception {
        //when
        Long produktId = nachbestellungRequestDTO.getProduktId();
        Long nachbestellungId = nachbestellung.getId();
        System.out.println("produktId " + produktId);
        when(nachbestellRepository.findByProduktId(produktId)).thenReturn(Optional.of(nachbestellung));
        //then
        nachbestellService.deleteNachbestellung(nachbestellungRequestDTO);
        //assert
        verify(nachbestellRepository, times(1)).deleteById(nachbestellungId);
    }*/

    @Test
    public void testDeleteNachbestellung_Failure() throws Exception {
        assertThrows(NachbestellungBereitsBeendetException.class, () -> {
            nachbestellService.deleteNachbestellung(nachbestellungRequestDTO);
        });

        verify(nachbestellRepository, never()).deleteById(any());
    }
}
