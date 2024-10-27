package be.boets.addressbook.country;

import be.boets.addressbook.domain.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryJpaRepository countryRepository;
    private final CountryClient countryClient;


    public CountryService(CountryJpaRepository countryRepository, CountryClient countryClient) {
        this.countryRepository = countryRepository;
        this.countryClient = countryClient;
    }

    public boolean isInitDataLoaded() {
        return !countryRepository.findAll().isEmpty();
    }

    /**
     * This method is used to load all countries from the api.
     * Once loaded, will store them all in the database.
     *
     * @return true if the countries are loaded successfully, false otherwise.
     */
    public boolean loadAllCountries () throws Exception{
        List<Country> countries = countryClient.getCountries();
        countryRepository.saveAll(countries);
        return isInitDataLoaded();
    }
}
