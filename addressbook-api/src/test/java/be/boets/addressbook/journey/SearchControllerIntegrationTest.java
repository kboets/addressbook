package be.boets.addressbook.journey;

import be.boets.addressbook.domain.Address;
import be.boets.addressbook.domain.Country;
import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.PersonDto;
import be.boets.addressbook.dto.SearchCriteria;
import be.boets.addressbook.mapper.PersonMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class SearchControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    private static final String SEARCH_URI = "/v1/search";
    private static final String PERSON_URI = "/v1/person";
    @Autowired
    private PersonMapper personMapper;
    private PersonDto personDto;
    private SearchCriteria searchCriteria;

    @BeforeEach
    void init() {
        Person person = buildPerson();
        personDto = personMapper.toDto(person);
        // Assuming you have an endpoint to create a person for testing purposes
        webTestClient.post()
                .uri(PERSON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(personDto), PersonDto.class)
                .exchange()
                .expectStatus().isOk();
    }
    @AfterEach
    void cleanUp() {
        List<PersonDto> persons = webTestClient.get()
                .uri(PERSON_URI + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(PersonDto.class)
                .getResponseBody()
                .collectList().block();

        assert persons != null;
        assertThat(persons.isEmpty()).isFalse();
        PersonDto lastInserted = persons.get(persons.size() - 1);
        webTestClient.delete()
                .uri(PERSON_URI + "/{id}", lastInserted.id())
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    public void search_givenOnlyLastName_shouldReturnPerson() {
        searchCriteria = new SearchCriteria("Doe", null, null, null, null, null, null);
        webTestClient.post()
                .uri(SEARCH_URI)
                .bodyValue(searchCriteria)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .consumeWith(response -> {
                    List<PersonDto> persons = response.getResponseBody();
                    assertNotNull(persons);
                    assertEquals(1, persons.size());
                    assertEquals("DOE", persons.get(0).name());
                });
    }

    @Test
    public void search_givenOnlyFirstName_shouldReturnPerson() {
        searchCriteria = new SearchCriteria(null, "Joe", null, null, null, null, null);
        webTestClient.post()
                .uri(SEARCH_URI)
                .bodyValue(searchCriteria)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .consumeWith(response -> {
                    List<PersonDto> persons = response.getResponseBody();
                    assertNotNull(persons);
                    assertEquals(1, persons.size());
                    assertEquals("DOE", persons.get(0).name());
                });
    }

    @Test
    public void search_givenCorrectLastAndFirstName_shouldReturnPerson() {
        searchCriteria = new SearchCriteria("Doe", "Joe", null, null, null, null, null);
        webTestClient.post()
                .uri(SEARCH_URI)
                .bodyValue(searchCriteria)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .consumeWith(response -> {
                    List<PersonDto> persons = response.getResponseBody();
                    assertNotNull(persons);
                    assertEquals(1, persons.size());
                    assertEquals("DOE", persons.get(0).name());
                });
    }

    @Test
    public void search_givenCorrectLastAndFalseFirstName_shouldReturnNoPerson() {
        searchCriteria = new SearchCriteria("Doe", "Peter", null, null, null, null, null);
        webTestClient.post()
                .uri(SEARCH_URI)
                .bodyValue(searchCriteria)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .consumeWith(response -> {
                    List<PersonDto> persons = response.getResponseBody();
                    assertNotNull(persons);
                    assertTrue(persons.isEmpty());
                });
    }

    @Test
    public void search_givenBirthDateAndName_shouldReturnPerson() {
        searchCriteria = new SearchCriteria("Doe", null, LocalDate.of(1978, 1, 30), null, null, null, null);
        webTestClient.post()
                .uri(SEARCH_URI)
                .bodyValue(searchCriteria)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .consumeWith(response -> {
                    List<PersonDto> persons = response.getResponseBody();
                    assertNotNull(persons);
                    assertEquals(1, persons.size());
                    assertEquals("DOE", persons.get(0).name());
                });
    }


    @Test
    public void search_givenBirthDateAndNameAndFalseCity_shouldReturnNoPerson() {
        searchCriteria = new SearchCriteria("Doe", null, LocalDate.of(1978, 1, 30), null, null, null, "Averbode");
        webTestClient.post()
                .uri(SEARCH_URI)
                .bodyValue(searchCriteria)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .consumeWith(response -> {
                    List<PersonDto> persons = response.getResponseBody();
                    assertNotNull(persons);
                    assertTrue(persons.isEmpty());
                });
    }


    private Person buildPerson() {
        return Person.PersonBuilder.aPerson()
                .withName("DOE")
                .withFirstName("joe")
                .withEmail("joh.doe@gmail.com")
                .withBirthDate(LocalDate.of(1978, 1, 30))
                .withMainAddress(buildAddress())
                .build();
    }

    private Address buildAddress() {
        return Address.AddressBuilder.anAddress()
                .withStreet("Schoolstraat")
                .withCity("Neerpelt")
                .withHouseNumber("5")
                .withZipCode("3910")
                .withCountry(buildCountry())
                .build();
    }

    private Country buildCountry() {
        return Country.CountryBuilder.aCountry()
                .withId(53)
                .withName("Belgium")
                .withCountryCode("BE")
                .build();
    }
}
