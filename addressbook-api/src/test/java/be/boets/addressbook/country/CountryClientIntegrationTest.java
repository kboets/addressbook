package be.boets.addressbook.country;

import be.boets.addressbook.domain.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CountryClientIntegrationTest {

    @Autowired
    private CountryClient countryClient;

    @Test
    public void getCountries_shouldReturnAllEuropeanCountries() throws Exception {
        List<Country> countryList = countryClient.getCountries();
        assertThat(countryList.isEmpty()).isFalse();
    }
}
