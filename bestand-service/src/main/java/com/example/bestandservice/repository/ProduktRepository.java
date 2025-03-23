package com.example.bestandservice.repository;

import com.example.bestandservice.model.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProduktRepository extends JpaRepository<Produkt, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Produkt p SET p.gesamtMenge = :menge WHERE p.id = :id")
    void updateBestandMenge(@Param("id") Long id, @Param("menge") Integer menge);
}
