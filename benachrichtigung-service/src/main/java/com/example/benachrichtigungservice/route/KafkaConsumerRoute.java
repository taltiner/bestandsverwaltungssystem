package com.example.benachrichtigungservice.route;

import com.example.benachrichtigungservice.dto.BestellStatusDTO;
import com.example.benachrichtigungservice.service.BenachrichtigungService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerRoute extends RouteBuilder {

    private final BenachrichtigungService benachrichtigungService;

    public KafkaConsumerRoute(BenachrichtigungService benachrichtigungService) {
        this.benachrichtigungService = benachrichtigungService;
    }

    @Override
    public void configure() throws Exception {
        from("kafka:bestellstatus?brokers=kafka:9092")
                .unmarshal().json(JsonLibrary.Jackson, BestellStatusDTO.class)
                .log("Empfangener Bestellstatus: ${body}")
                .bean(benachrichtigungService, "updateBestellStatus");
    }
}
