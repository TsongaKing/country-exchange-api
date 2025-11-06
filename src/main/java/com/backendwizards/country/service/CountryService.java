package com.backendwizards.country.service;

import com.backendwizards.country.entity.Country;
import com.backendwizards.country.repository.CountryRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CountryService {

    private final CountryRepository repo;
    private final ApplicationEventPublisher eventPublisher;

    public CountryService(CountryRepository repo, ApplicationEventPublisher eventPublisher) {
        this.repo = repo;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Refresh database with dummy countries
     */
    @Transactional
    public Instant refreshAllAndReturnTimestamp() {
        Instant now = Instant.now();

        List<Country> dummyCountries = List.of(
                createCountry("South Africa", "Pretoria", "Africa", 60_000_000L, "ZAR", 1.0, 5e11, "https://flagcdn.com/za.svg", now),
                createCountry("United States", "Washington D.C.", "Americas", 331_000_000L, "USD", 1.0, 2.1e13, "https://flagcdn.com/us.svg", now),
                createCountry("Japan", "Tokyo", "Asia", 125_800_000L, "JPY", 110.0, 5e12, "https://flagcdn.com/jp.svg", now)
        );

        for (Country c : dummyCountries) {
            repo.findByNameIgnoreCase(c.getName()).ifPresentOrElse(
                    existing -> {
                        // update existing
                        existing.setCapital(c.getCapital());
                        existing.setRegion(c.getRegion());
                        existing.setPopulation(c.getPopulation());
                        existing.setCurrencyCode(c.getCurrencyCode());
                        existing.setExchangeRate(c.getExchangeRate());
                        existing.setEstimatedGdp(c.getEstimatedGdp());
                        existing.setFlagUrl(c.getFlagUrl());
                        existing.setLastRefreshedAt(c.getLastRefreshedAt());
                        repo.save(existing);
                    },
                    () -> repo.save(c)
            );
        }

        eventPublisher.publishEvent(new RefreshCompletedEvent(this, now));
        return now;
    }

    private Country createCountry(String name, String capital, String region, Long population,
                                  String currencyCode, Double exchangeRate, Double gdp,
                                  String flagUrl, Instant lastRefreshedAt) {
        Country c = new Country();
        c.setName(name);
        c.setCapital(capital);
        c.setRegion(region);
        c.setPopulation(population);
        c.setCurrencyCode(currencyCode);
        c.setExchangeRate(exchangeRate);
        c.setEstimatedGdp(gdp);
        c.setFlagUrl(flagUrl);
        c.setLastRefreshedAt(lastRefreshedAt);
        return c;
    }

    public static class RefreshCompletedEvent {
        private final Object source;
        private final Instant timestamp;
        public RefreshCompletedEvent(Object source, Instant timestamp) {
            this.source = source;
            this.timestamp = timestamp;
        }
        public Object getSource() { return source; }
        public Instant getTimestamp() { return timestamp; }
    }

    public long count() { return repo.count(); }

    public Optional<Country> findByName(String name) { return repo.findByNameIgnoreCase(name); }

    public List<Country> findAllFiltered(Optional<String> region, Optional<String> currency, Optional<String> sort) {
        List<Country> all = repo.findAll();
        Stream<Country> s = all.stream();
        if (region.isPresent()) s = s.filter(c -> c.getRegion() != null && region.get().equalsIgnoreCase(c.getRegion()));
        if (currency.isPresent()) s = s.filter(c -> c.getCurrencyCode() != null && currency.get().equalsIgnoreCase(c.getCurrencyCode()));
        if (sort.isPresent()) {
            if (sort.get().equalsIgnoreCase("gdp_desc"))
                s = s.sorted(Comparator.comparing(Country::getEstimatedGdp, Comparator.nullsLast(Comparator.reverseOrder())));
            else if (sort.get().equalsIgnoreCase("gdp_asc"))
                s = s.sorted(Comparator.comparing(Country::getEstimatedGdp, Comparator.nullsLast(Comparator.naturalOrder())));
        }
        return s.collect(Collectors.toList());
    }

    public void deleteByName(String name) {
        if (!repo.existsByNameIgnoreCase(name)) throw new NoSuchElementException("Country not found");
        repo.deleteByNameIgnoreCase(name);
    }

    public Optional<Instant> getLatestRefresh() {
        Instant t = repo.findLatestRefreshTimestamp();
        return Optional.ofNullable(t);
    }
}
