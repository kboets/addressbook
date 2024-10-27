package be.boets.addressbook.search;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.SearchCriteria;
import be.boets.addressbook.person.PersonDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    private SearchService underTest;
    @Mock
    private PersonDao personDao;

    @BeforeEach
    void setUp() {
        underTest = new SearchService(personDao);
    }

    @Test
    void search() {
        // Given
        SearchCriteria searchCriteria = new SearchCriteria("Doe", null, null, null, null, null, null);
        List<Person> expectedPersons = new ArrayList<>();
        Person person = Person.PersonBuilder.aPerson().withName("DOE").build();
        expectedPersons.add(person);
        // When
        when(personDao.search(searchCriteria)).thenReturn(expectedPersons);
        List<Person> result = underTest.search(searchCriteria);
        // Then
        verify(personDao).search(searchCriteria);
        assertThat(result).isEqualTo(expectedPersons);
    }
}