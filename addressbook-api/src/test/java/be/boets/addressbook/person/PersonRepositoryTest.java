package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.repository.AbstractTestcontainersUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest extends AbstractTestcontainersUnitTest {

    @Autowired
    private PersonRepository personRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void existsPersonByEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress();
        LocalDate birthDate = FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Person person = new Person(FAKER.name().lastName(), FAKER.name().firstName(), birthDate, null, null, email);

        personRepository.save(person);

        // When
        var actual = personRepository.existsPersonByEmail(email);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonByEmail_givenNonExistingId_shouldReturnFalse() {
        // Given
        String email = FAKER.internet().safeEmailAddress();
        // When
        var actual = personRepository.existsPersonByEmail(email);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsPersonById() {
        // Given
        String email = FAKER.internet().safeEmailAddress();
        LocalDate birthDate = FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Person person = new Person(FAKER.name().lastName(), FAKER.name().firstName(), birthDate, null, null, email);

        personRepository.save(person);

        int id = personRepository.findAll()
                .stream()
                .filter(p -> p.getEmail().equals(email))
                .map(Person::getId)
                .findFirst()
                .orElseThrow();

        // When
        var actual = personRepository.existsPersonById(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonById_givenInvalidId_shouldFail() {
        // Given
        int id = -1;

        // When
        var actual = personRepository.existsPersonById(id);

        // Then
        assertThat(actual).isFalse();
    }

//    @Test
//    void search_givenValidPersonCriteria_shouldReturnPerson() {
//        // Given
//        String email = FAKER.internet().safeEmailAddress();
//        LocalDate birthDate = FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        String name = FAKER.name().lastName();
//        String fistName = FAKER.name().firstName();
//
//        Person person = new Person(name, fistName, birthDate, null, null, email);
//
//        personRepository.savePerson(person);
//        SearchCriteria criteria = new SearchCriteria(name, null, null, null, null, null);
//
//        // When
//        var actual = personRepository.search(criteria);
//
//        // Then
//        assertThat(actual.size()).isEqualTo(1);
//        assertThat(actual).contains(person);
//    }
}