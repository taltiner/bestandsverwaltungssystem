package com.example.bestandservice.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:sendNachbestellung")
                .marshal().json()
                .log("Sende Nachbestellung: ${body}")
                .to("kafka:nachbestellung?brokers=kafka:9092");

        from("direct:sendBestellstatus")
                .marshal().json()
                .log("Sende Bestellstatus: ${body}")
                .to("kafka:bestellstatus?brokers=kafka:9092");
    }
}
