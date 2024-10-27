package be.boets.addressbook.city;

import be.boets.addressbook.domain.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CityClientIntegrationTest {

    @Autowired
    private CityClient cityClient;

    @Test
    public void retrieveCity_givenMainCity_shouldOnlyReturnMainCity() throws Exception {
        Set<City> cities = cityClient.retrieveCity("3500");
        assertThat(cities.size()).isEqualTo(1);
        City city = cities.iterator().next();
        assertThat(city.getName()).isEqualTo("Hasselt");
        assertThat(city.getZipCode()).isEqualTo("3500");
        assertThat(city.getCountryCode()).isEqualTo("BE");
    }



}
