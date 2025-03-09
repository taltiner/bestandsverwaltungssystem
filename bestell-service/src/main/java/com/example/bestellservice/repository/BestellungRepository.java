package com.example.bestellservice.repository;

import com.example.bestellservice.model.Bestellung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellungRepository extends JpaRepository<Bestellung, Long> {
}
