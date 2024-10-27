package be.boets.addressbook.journey;

import be.boets.addressbook.dto.AddressDto;
import be.boets.addressbook.dto.CountryDto;
import be.boets.addressbook.dto.PersonDto;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    private static final String PERSON_URI = "/v1/person";

    protected static final Faker FAKER = new Faker();
    private Random random;

    @BeforeEach
    void init() {
        random = new Random();

    }

    @Test
    void registerPerson() {
        // Given
        int id = random.nextInt();
        Name name = FAKER.name();
        String email = name.lastName() + "." + name.firstName() + "@foobarhello123.com";
        LocalDate birthDate = FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        PhoneNumber phoneNumber = FAKER.phoneNumber();
        int mainAddressId = random.nextInt();
        Address address = FAKER.address();
        CountryDto countryDto = new CountryDto(53, "Belgie", "0032");
        AddressDto addressDto = new AddressDto(mainAddressId, address.streetName(), address.streetAddressNumber(), null, address.zipCode(), address.city(), countryDto);
        PersonDto personDto = new PersonDto(id, name.lastName(), name.lastName(), birthDate, null, phoneNumber.phoneNumber(), email, addressDto);
        // When

        webTestClient.post()
                .uri(PERSON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(personDto), PersonDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void deletePerson() {
        // setup a person
        int id = random.nextInt();
        Name name = FAKER.name();
        String email = name.lastName() + "." + name.firstName() + "@foobarhello123.com";
        LocalDate birthDate = FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        PhoneNumber phoneNumber = FAKER.phoneNumber();
        int mainAddressId = random.nextInt();
        Address address = FAKER.address();
        CountryDto countryDto = new CountryDto(53, "Belgie", "0032");
        AddressDto addressDto = new AddressDto(mainAddressId, address.streetName(), address.streetAddressNumber(), null, address.zipCode(), address.city(), countryDto);
        PersonDto personDto = new PersonDto(id, name.lastName(), name.lastName(), birthDate, null, phoneNumber.phoneNumber(), email, addressDto);


        //add a new person
        webTestClient.post()
                .uri(PERSON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(personDto), PersonDto.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all persons
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

        // delete the post request
        webTestClient.delete()
                .uri(PERSON_URI + "/{id}", lastInserted.id())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // get person again -> should return 404
        webTestClient.get()
                .uri(PERSON_URI + "/{id}", lastInserted.id())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void updatePerson() {
        // setup a person
        int id = random.nextInt();
        String firstName = "Jantje";
        String lastName = "Schildermans";
        String email = lastName + "." + firstName +"."+ random.nextInt()+"@foobarhello.com";
        LocalDate birthDate = FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        PhoneNumber phoneNumber = FAKER.phoneNumber();
        int mainAddressId = random.nextInt();
        Address address = FAKER.address();
        CountryDto countryDto = new CountryDto(53, "Belgie", "0032");
        AddressDto addressDto = new AddressDto(mainAddressId, address.streetName(), address.streetAddressNumber(), null, address.zipCode(), address.city(), countryDto);
        PersonDto personDto = new PersonDto(id, lastName, lastName, birthDate, null, phoneNumber.phoneNumber(), email, addressDto);


        //add a new person
        webTestClient.post()
                .uri(PERSON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(personDto), PersonDto.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all persons
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

        int currentAmountOfPeople = persons.size();

        PersonDto lastInserted = persons.get(persons.size() - 1);
        String newEmail = lastName + "." + firstName +"."+ random.nextInt()+"@grack.com";
        // update this persons email address
        PersonDto updatePerson = PersonDto.PersonDtoBuilder.aPersonDto()
                .withBirthDate(lastInserted.birthDate())
                .withFirstName(lastInserted.firstName())
                .withName(lastInserted.name())
                .withId(lastInserted.id())
                .withMobilePhone(lastInserted.mobilePhone())
                .withPhoneNumber(lastInserted.phoneNumber())
                .withEmail(newEmail)
                .withMainAddress(lastInserted.mainAddress())
                .build();


        // call the update method
        webTestClient.put()
                .uri(PERSON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatePerson), PersonDto.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all persons again
        persons = webTestClient.get()
                .uri(PERSON_URI + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(PersonDto.class)
                .getResponseBody()
                .collectList().block();

        assert persons != null;
        assertThat(persons.isEmpty()).isFalse();
        assertThat(persons.size()).isEqualTo(currentAmountOfPeople);

        PersonDto newLastInserted = persons.get(persons.size() - 1);
        assertThat(newLastInserted.id()).isEqualTo(lastInserted.id());
        assertThat(newLastInserted.email()).isEqualTo(newEmail);
    }
}
