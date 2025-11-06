package com.backendwizards.country.config;

import com.backendwizards.country.entity.Country;
import com.backendwizards.country.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DummyDataLoader implements CommandLineRunner {

    private final CountryRepository countryRepository;

    public DummyDataLoader(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(String... args) {
        if (countryRepository.count() > 0) {
            // Data already exists, skip
            return;
        }

        Instant now = Instant.now();

        Country usa = new Country();
        usa.setName("United States");
        usa.setCapital("Washington D.C.");
        usa.setRegion("Americas");
        usa.setCurrencyCode("USD");
        usa.setExchangeRate(1.0);
        usa.setPopulation(331_000_000L);
        usa.setEstimatedGdp(25_000_000_000_000.0);
        usa.setFlagUrl("https://flagcdn.com/us.svg");
        usa.setLastRefreshedAt(now);

        Country germany = new Country();
        germany.setName("Germany");
        germany.setCapital("Berlin");
        germany.setRegion("Europe");
        germany.setCurrencyCode("EUR");
        germany.setExchangeRate(0.92);
        germany.setPopulation(83_000_000L);
        germany.setEstimatedGdp(4_500_000_000_000.0);
        germany.setFlagUrl("https://flagcdn.com/de.svg");
        germany.setLastRefreshedAt(now);

        Country japan = new Country();
        japan.setName("Japan");
        japan.setCapital("Tokyo");
        japan.setRegion("Asia");
        japan.setCurrencyCode("JPY");
        japan.setExchangeRate(150.0);
        japan.setPopulation(126_000_000L);
        japan.setEstimatedGdp(5_000_000_000_000.0);
        japan.setFlagUrl("https://flagcdn.com/jp.svg");
        japan.setLastRefreshedAt(now);

        countryRepository.save(usa);
        countryRepository.save(germany);
        countryRepository.save(japan);

        System.out.println("Inserted dummy countries: USA, Germany, Japan");
    }
}
