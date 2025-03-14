package com.example.bestandservice;

import com.example.bestandservice.service.BestandService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private final BestandService bestandService;

    public KafkaListeners(BestandService bestandService) {
        this.bestandService = bestandService;
    }

    @KafkaListener(
            topics = "bestellung",
            groupId = "groupId"
    )
    void listener(String data) {

        System.out.println("Listener received: " + data);
        bestandService.pruefeObMindestBestandErreicht(data);
    }
}
