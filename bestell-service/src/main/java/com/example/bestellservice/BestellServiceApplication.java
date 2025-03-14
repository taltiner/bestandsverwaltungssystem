package com.example.bestellservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;


@SpringBootApplication
public class BestellServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BestellServiceApplication.class, args);
	}

}
