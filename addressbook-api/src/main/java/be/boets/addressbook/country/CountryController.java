package be.boets.addressbook.country;

import be.boets.addressbook.dto.CountryDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    //get all
    @GetMapping("/all")
    public List<CountryDto> getAllCountries() {
        return countryService.getAllCountries();
    }
}
