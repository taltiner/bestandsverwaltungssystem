package com.example.nachbestellservice.route;

import com.example.nachbestellservice.dto.request.NachbestellungRequestDTO;
import com.example.nachbestellservice.service.NachbestellService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class KafkaConsumerRoute extends RouteBuilder {

    private final NachbestellService nachbestellService;

    public KafkaConsumerRoute(NachbestellService nachbestellService) {
        this.nachbestellService = nachbestellService;
    }

    @Override
    public void configure() throws Exception {
        from("kafka:nachbestellung?brokers=kafka:9092")
                .unmarshal().json(JsonLibrary.Jackson, NachbestellungRequestDTO.class)
                .log("Empfangene Nachbestellung: ${body}")
                .bean(nachbestellService, "saveNachbestellung");

        from("direct:sendNachbestellungLoeschen")
                .marshal().json()
                .log("Sende Nachbestellung Loeschen ${body}")
                .to("kafka:nachbestellung-loeschen?brokers=kafka:9092");
    }
}
