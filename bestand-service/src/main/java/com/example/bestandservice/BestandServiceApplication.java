package com.example.bestandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.bestandservice.model")
@ComponentScan(basePackages = "com.example.bestandservice.repository")
public class BestandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.example.bestandservice.BestandServiceApplication.class, args);
	}

}
