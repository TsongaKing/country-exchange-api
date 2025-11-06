package com.backendwizards.country.controller;

import com.backendwizards.country.entity.Country;
import com.backendwizards.country.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService svc;

    public CountryController(CountryService svc) {
        this.svc = svc;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh() {
        Instant ts = svc.refreshAllAndReturnTimestamp();
        Map<String, Object> r = new HashMap<>();
        r.put("message", "Refresh successful");
        r.put("last_refreshed_at", ts.toString());
        return ResponseEntity.ok(r);
    }

    @GetMapping
    public ResponseEntity<List<Country>> list(
            @RequestParam Optional<String> region,
            @RequestParam Optional<String> currency,
            @RequestParam Optional<String> sort) {
        List<Country> countries = svc.findAllFiltered(region, currency, sort);
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getOne(@PathVariable String name) {
        return svc.findByName(name)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Country not found")));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        try {
            svc.deleteByName(name);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Country not found"));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        Map<String, Object> m = new HashMap<>();
        m.put("total_countries", svc.count());
        Optional<Instant> last = svc.getLatestRefresh();
        m.put("last_refreshed_at", last.map(Instant::toString).orElse(null));
        return ResponseEntity.ok(m);
    }
}
