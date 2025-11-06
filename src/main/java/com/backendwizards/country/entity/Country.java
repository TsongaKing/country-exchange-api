package com.backendwizards.country.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "countries", indexes = {
        @Index(name = "IDX_NAME", columnList = "name")
})
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String capital;
    private String region;

    private Long population;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @Column(name = "estimated_gdp")
    private Double estimatedGdp;

    @Column(name = "last_refreshed_at")
    private Instant lastRefreshedAt;

    @Column(name = "flag_url")
    private String flagUrl;

    // Constructors
    public Country() {}

    public Country(String name, String capital, String region, Long population,
                   String currencyCode, Double exchangeRate, Double estimatedGdp,
                   Instant lastRefreshedAt, String flagUrl) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.population = population;
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.estimatedGdp = estimatedGdp;
        this.lastRefreshedAt = lastRefreshedAt;
        this.flagUrl = flagUrl;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCapital() { return capital; }
    public void setCapital(String capital) { this.capital = capital; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public Long getPopulation() { return population; }
    public void setPopulation(Long population) { this.population = population; }

    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }

    public Double getExchangeRate() { return exchangeRate; }
    public void setExchangeRate(Double exchangeRate) { this.exchangeRate = exchangeRate; }

    public Double getEstimatedGdp() { return estimatedGdp; }
    public void setEstimatedGdp(Double estimatedGdp) { this.estimatedGdp = estimatedGdp; }

    public Instant getLastRefreshedAt() { return lastRefreshedAt; }
    public void setLastRefreshedAt(Instant lastRefreshedAt) { this.lastRefreshedAt = lastRefreshedAt; }

    public String getFlagUrl() { return flagUrl; }
    public void setFlagUrl(String flagUrl) { this.flagUrl = flagUrl; }
}
