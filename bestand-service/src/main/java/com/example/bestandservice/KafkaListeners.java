package com.example.bestandservice;

import com.example.bestandservice.service.BestandService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

//@Component Deprecated durch Apache Camel
public class KafkaListeners {

/*    private final BestandService bestandService;

    public KafkaListeners(BestandService bestandService) {
        this.bestandService = bestandService;
    }

    @KafkaListener(
            topics = {"bestellung", "nachbestellung-loeschen"},
            groupId = "groupId"
    )
    void listener(@Payload String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws Exception {

        System.out.println("Listener received: " + data + " from topic: " + topic);
        if("bestellung".equals(topic)) {
            bestandService.pruefeObMindestBestandErreicht(data);
        } else if("nachbestellung-loeschen".equals(topic)){
            bestandService.setMaxMenge(data);
        } else {
            return;
        }
    }*/
}
