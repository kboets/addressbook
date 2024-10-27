package be.boets.addressbook.person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class PersonJpaRepositoryTest {

    private PersonJpaRepository underTest;
    @Mock
    private PersonRepository personRepository;
    private AutoCloseable autoCloseable;
//    @Mock
//    private Specification<Person> expectedSpecification; // Create a Specification to be returned by SearchSpecs.searchByCriteria

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new PersonJpaRepository(personRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getPersonById() {
        // Given
        Integer id = 5;
        // When
        underTest.getPersonById(id);
        // Then
        verify(personRepository).findById(id);
    }

    @Test
    void deletePerson() {
        // Given
        Integer id = 5;
        // When
        underTest.deletePerson(id);
        // Then
        verify(personRepository).deleteById(id);
    }
}