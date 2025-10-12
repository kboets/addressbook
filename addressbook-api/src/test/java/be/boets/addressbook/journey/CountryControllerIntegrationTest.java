package be.boets.addressbook.journey;

import be.boets.addressbook.dto.CountryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CountryControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    private static final String COUNTRY_URI = "/v1/country";

    @Test
    void getAllCountries() {
        List<CountryDto> countryDtoList = webTestClient.get().uri(COUNTRY_URI + "/all")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(CountryDto.class)
                .getResponseBody()
                .collectList().block();
        
        assertNotNull(countryDtoList);
        assertFalse(countryDtoList.isEmpty());
        
        // Verify that countries have expected properties
        CountryDto countryDto = countryDtoList.get(0);
        assertNotNull(countryDto.id());
        assertNotNull(countryDto.name());
        assertNotNull(countryDto.countryCode());
    }
}
