package com.example.benachrichtigungservice.repository;

import com.example.benachrichtigungservice.model.BestellStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenachrichtigungRepository extends JpaRepository<BestellStatus, Long> {
    BestellStatus findByBestellId(Long bestellId);
}
