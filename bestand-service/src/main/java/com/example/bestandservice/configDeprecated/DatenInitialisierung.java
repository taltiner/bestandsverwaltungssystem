package com.example.bestandservice.configDeprecated;

import com.example.bestandservice.model.Kategorie;
import com.example.bestandservice.model.Produkt;
import com.example.bestandservice.repository.ProduktRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatenInitialisierung implements CommandLineRunner {
    private final ProduktRepository produktRepository;

    public DatenInitialisierung(ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Produkt produkt = Produkt.builder()
            .detailInfo("Testprodukt")
            .name("Handy")
            .preis(120.00)
            .kategorie(Kategorie.SMARTPHONE)
            .gesamtMenge(20)
            .mindestMenge(10)
            .maxMenge(30)
            .build();

        produktRepository.save(produkt);
    }
}
