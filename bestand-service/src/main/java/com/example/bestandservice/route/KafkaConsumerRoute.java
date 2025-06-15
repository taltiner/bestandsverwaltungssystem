package com.example.bestandservice.route;

import com.example.bestandservice.dto.request.BestellungRequestDTO;
import com.example.bestandservice.dto.request.NachbestellRequestDTO;
import com.example.bestandservice.service.BestandService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerRoute extends RouteBuilder {

    private final BestandService bestandService;

    public KafkaConsumerRoute(BestandService bestandService) {
        this.bestandService = bestandService;
    }

    @Override
    public void configure() throws Exception {
        from("kafka:bestellung?brokers=kafka:9092")
                .unmarshal().json(JsonLibrary.Jackson, BestellungRequestDTO.class)
                .log("Empfangen Bestellung: ${body}")
                .bean(bestandService, "pruefeObMindestBestandErreicht");

        from("kafka:nachbestellung-loeschen?brokers=kafka:9092")
                .unmarshal().json(JsonLibrary.Jackson, NachbestellRequestDTO.class)
                .log("Folgende Nachbestellung wird geloescht: ${body}")
                .bean(bestandService, "setMaxMenge");
    }
}
