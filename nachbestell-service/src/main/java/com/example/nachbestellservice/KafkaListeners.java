package com.example.nachbestellservice;

import com.example.nachbestellservice.service.NachbestellService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component - Deprecated durch Apache Camel
public class KafkaListeners {

/*    private final NachbestellService nachbestellService;

    public KafkaListeners(NachbestellService nachbestellService) {
        this.nachbestellService = nachbestellService;
    }

    @KafkaListener(
            topics = "nachbestellung",
            groupId = "groupId"
    )
    void listener(String data) {

        System.out.println("Nachbestellung Listener received: " + data);
        nachbestellService.saveNachbestellung(data);
    }*/
}
