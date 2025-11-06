package com.backendwizards.country.repository;

import com.backendwizards.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.Instant;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByNameIgnoreCase(String name);
    void deleteByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);

    @Query("select max(c.lastRefreshedAt) from Country c")
    Instant findLatestRefreshTimestamp();
}
