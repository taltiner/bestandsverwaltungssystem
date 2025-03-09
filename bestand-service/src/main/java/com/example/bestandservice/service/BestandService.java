package com.example.bestandservice.service;

import com.example.bestandservice.model.Produkt;
import com.example.bestandservice.repository.ProduktRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BestandService {

    private final ProduktRepository produktRepository;

    public void saveProdukt(Produkt produkt) {
        produktRepository.save(produkt);
    }

    public void updateProdukt(Long id, Produkt produkt) {
        Produkt vorhandenesProdukt = produktRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produkt mit ID " + id + " nicht gefunden"));

        vorhandenesProdukt.setKategorie(produkt.getKategorie());
        vorhandenesProdukt.setName(produkt.getName());
        vorhandenesProdukt.setPreis(produkt.getPreis());
        vorhandenesProdukt.setDetailInfo(produkt.getDetailInfo());
        vorhandenesProdukt.setGesamtMenge(produkt.getGesamtMenge());



        produktRepository.save(vorhandenesProdukt);
    }
}
