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

    void search() {
        // Given
        // TODO create a way to mock static methods in junit 5 -> not clear now
//        SearchCriteria searchCriteria = new SearchCriteria("Doe", null, null, null, null, null, null);
//        List<Person> expectedPersons = new ArrayList<>();
//        Person person = Person.PersonBuilder.aPerson().withName("DOE").build();
//        expectedPersons.add(person);
//        MockedStatic<Specification> expectedSpecification = mockStatic(Specification.class);
//        // When
//        Mockito.when(SearchSpecs.searchByCriteria(expectedPersons)).thenReturn()
//        when(SearchSpecs.searchByCriteria(searchCriteria)).thenReturn(expectedSpecification);
//        when(personRepository.findAll(expectedSpecification)).thenReturn(expectedPersons);
//        List<Person> result = underTest.search(searchCriteria);
//
//        // Then
//        verify(personRepository).findAll(expectedSpecification);
//        assertEquals(expectedPersons, result);

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