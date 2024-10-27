package be.boets.addressbook.city;

import be.boets.addressbook.config.ClientProperties;
import be.boets.addressbook.domain.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CityClientTest {
    @Mock
    private ClientProperties clientProperties;

    @InjectMocks
    private CityClient cityClient;

    @BeforeEach
    void setUp() {
        openMocks(this);
        when(clientProperties.getCityUrl()).thenReturn("http://mock.api");
    }

    @Test
    public void retrieveCity_givenMainCity_shouldReturnOneCity()  {
        Set<City> cityList = cityClient.parseResponse(CityClientMockData.getMainCity(), "3270");
        assertThat(cityList.size()).isEqualTo(1);
        City city = cityList.iterator().next();
        assertThat(city.getName()).isEqualTo("Scherpenheuvel-Zichem");
    }

    @Test
    public void retrieveCity_givenSubCity_shouldReturnMoreCities() {
        Set<City> cityList = cityClient.parseResponse(CityClientMockData.getSubCity(), "3271");
        assertThat(cityList.size()).isEqualTo(2);
        City averbodeCity = cityList.stream()
                .filter(city -> city.getName().equals("Averbode"))
                .findFirst()
                .orElse(null);
        assertThat(averbodeCity).isNotNull();
    }
}
