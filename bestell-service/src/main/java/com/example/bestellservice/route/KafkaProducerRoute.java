package com.example.bestellservice.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:sendBestellung")
                .marshal().json()
                .log("Sende Bestellung: ${body}")
                .to("kafka:bestellung?brokers=kafka:9092");
    }
}
