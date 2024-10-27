package be.boets.addressbook.journey;

import be.boets.addressbook.dto.CityDto;
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
public class CityControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    private static final String CITY_URI = "/v1/city";

    @Test
    void getCityByZipCode() {
        List<CityDto> cityDtoList = webTestClient.get().uri(CITY_URI + "/3271")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(CityDto.class)
                .getResponseBody()
                .collectList().block();
        assertNotNull(cityDtoList);
        assertFalse(cityDtoList.isEmpty());
        assertEquals(2, cityDtoList.size());
        CityDto cityDto = cityDtoList.get(0);
        assertEquals("3271", cityDto.zipCode());
        assertFalse(cityDto.streetNames().isEmpty());
    }

    @Test
    void getAllCity() {
        List<CityDto> cityDtoList = webTestClient.get().uri(CITY_URI+"/all")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(CityDto.class)
                .getResponseBody()
                .collectList().block();
        assertNotNull(cityDtoList);
        assertFalse(cityDtoList.isEmpty());
    }
}


