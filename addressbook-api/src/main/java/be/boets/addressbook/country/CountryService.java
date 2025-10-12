package be.boets.addressbook.country;

import be.boets.addressbook.domain.Country;
import be.boets.addressbook.dto.CountryDto;
import be.boets.addressbook.mapper.CountryMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryJpaRepository countryRepository;
    private final CountryClient countryClient;
    private final CountryMapper countryMapper;


    public CountryService(CountryJpaRepository countryRepository, CountryClient countryClient, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryClient = countryClient;
        this.countryMapper = countryMapper;
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

    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return countryMapper.toDtos(countries);
    }
}
