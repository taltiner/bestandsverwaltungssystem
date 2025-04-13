package com.example.nachbestellservice.repository;

import com.example.nachbestellservice.model.Nachbestellung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NachbestellRepository extends JpaRepository<Nachbestellung, Long> {

    Optional<Nachbestellung> findByProduktId(Long produktId);
}
