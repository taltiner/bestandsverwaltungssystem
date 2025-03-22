package com.example.benachrichtigungservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(
            topics = "bestellstatus",
            groupId = "groupId"
    )
    void listener(String data) {

        System.out.println("Benachrichtigung Listener received: " + data);
    }

}
