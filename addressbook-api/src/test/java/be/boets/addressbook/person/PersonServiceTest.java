package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.exception.DuplicateResourceException;
import be.boets.addressbook.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    private PersonService underTest;
    @Mock
    private PersonDao personDao;

    @BeforeEach
    void setUp() {
        underTest = new PersonService(personDao);
    }

    @Test
    void findAll() {
        // When
        underTest.findAll();
        // Then
        verify(personDao).getPersons();
    }

    @Test
    void findById() {
        // Given
        int id = 10;
        Person person = Person.PersonBuilder.aPerson().withName("John").build();
        // When
        Mockito.when(personDao.getPersonById(id)).thenReturn(Optional.of(person));
        Person result = underTest.findById(id);
        // Then
        assertThat(result).isEqualTo(person);
    }

    @Test
    void findById_givenOptionalEmpty_shouldThrownException() {
        // Given
        int id = 10;
        // When
        Mockito.when(personDao.getPersonById(id)).thenReturn(Optional.empty());
        // Then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Could not find a person with id %s".formatted(id));
    }

    @Test
    void addPerson() {
        // Given
        String email = "johnDoh@gmail.com";
        when(personDao.existsEmailForPerson(email)).thenReturn(false);
        Person person = Person.PersonBuilder.aPerson().withName("John").withEmail(email).build();
        // When
        underTest.addPerson(person);

        // Then
        verify(personDao).savePerson(person);
    }

    @Test
    void addPerson_givenExistingEmail_shouldThrownException() {
        // Given
        String email = "johnDoh@gmail.com";
        Person person = Person.PersonBuilder.aPerson().withName("John").withEmail(email).build();
        // When
        Mockito.when(personDao.existsEmailForPerson(email)).thenReturn(true);
        // Then
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");
    }

    @Test
    void updatePerson() {
        // Given
        String email = "johnDoh@gmail.com";
        Person person = Person.PersonBuilder.aPerson().withName("John").withEmail(email).build();
        // When
        underTest.updatePerson(person);
        // Then
        verify(personDao).updatePerson(person);
    }

    @Test
    void deletePerson() {
        // Given
        int id = 10;
        Mockito.when(personDao.personExistsWithId(id)).thenReturn(true);
        // When
        underTest.deletePerson(id);
        // Then
        verify(personDao).deletePerson(id);
    }

    @Test
    void deletePerson_givenUnknownId_shouldThrowException() {
        // Given
        int id = 10;
        Mockito.when(personDao.personExistsWithId(id)).thenReturn(false);
        // Then
        assertThatThrownBy(() -> underTest.deletePerson(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Could not find a person with id %s".formatted(id));

        verify(personDao, never()).deletePerson(id);
    }

}