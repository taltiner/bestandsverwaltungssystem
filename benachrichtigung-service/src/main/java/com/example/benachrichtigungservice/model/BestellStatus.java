package com.example.benachrichtigungservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Data;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "BestellStatus")
@Builder
@Data
public class BestellStatus {
    @jakarta.persistence.Id
    @SequenceGenerator(
            name = "benachrichtigung_sequence",
            sequenceName = "benachrichtigung_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "benachrichtigung_sequence"
    )
    private Long id;
    private Long bestellId;
    private Status status;
}

