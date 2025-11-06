package com.backendwizards.country.listener;

import com.backendwizards.country.entity.Country;
import com.backendwizards.country.service.CountryService;
import com.backendwizards.country.util.ImageGenerator;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;
import java.util.List;

@Component
public class ImageGeneratorListener {

    private final CountryService countryService;
    private final ImageGenerator imageGenerator;

    public ImageGeneratorListener(CountryService countryService, ImageGenerator imageGenerator) {
        this.countryService = countryService;
        this.imageGenerator = imageGenerator;
    }

    // AFTER_COMMIT ensures DB changes are persisted before we create the image
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleRefresh(com.backendwizards.country.service.CountryService.RefreshCompletedEvent event) {
        Instant ts = event.getTimestamp();
        List<Country> all = countryService.findAllFiltered(java.util.Optional.empty(), java.util.Optional.empty(), java.util.Optional.empty());
        imageGenerator.generateSummary(all, ts);
    }
}
