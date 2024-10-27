package be.boets.addressbook.common;

import be.boets.addressbook.city.CityService;
import be.boets.addressbook.country.CountryService;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ManagementService {

    private final CountryService countryService;
    private final CityService cityService;

    public ManagementService(CountryService countryService, CityService cityService) {
        this.countryService = countryService;
        this.cityService = cityService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initAllData() {
        RetryPolicy<Object> retryCountryPolicy = RetryPolicy.builder()
                .withDelay(Duration.ofSeconds(5))
                .handle(Exception.class)
                .withMaxAttempts(2)
                .build();

        Failsafe.with(retryCountryPolicy)
                .run(() -> {
                    // make sure all countries are loaded
                    if (!countryService.isInitDataLoaded()) {
                        countryService.loadAllCountries();
                    }
                    // make sure all cities are loaded
                    if (!cityService.isInitDataLoaded()) {
                        cityService.loadAllCities();
                    }
                });

    }
}
