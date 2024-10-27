package be.boets.addressbook.country;

import be.boets.addressbook.config.ClientProperties;
import be.boets.addressbook.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class CountryClientTest {

    @Mock
    private ClientProperties clientProperties;
    @InjectMocks
    private CountryClient underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clientProperties.getCountryUrl()).thenReturn("http://mock.api");
    }

    @Test
    public void getCountries_givenBECountry_shouldReturnCountry() {
        // when
        List<Country> countries = underTest.parseJson(CountryClientMockData.countryBE());
        assertThat(countries.size()).isEqualTo(1);
        Country country = countries.get(0);
        assertThat(country.getName()).isEqualTo("Belgium");
        assertThat(country.getPhoneCode()).isEqualTo("0032");
    }

    @Test
    public void getCountries_givenNonEuropeanCountry_shouldReturnEmptyList() {
        // when
        List<Country> countries = underTest.parseJson(CountryClientMockData.countryTN());
        assertThat(countries.isEmpty()).isTrue();
    }

    @Test
    public void getCountries_givenRUCountry_shouldReturnCountry() {
        // when
        List<Country> countries = underTest.parseJson(CountryClientMockData.countryRU());
        assertThat(countries.size()).isEqualTo(1);
        Country country = countries.get(0);
        assertThat(country.getName()).isEqualTo("Russia");
    }
}