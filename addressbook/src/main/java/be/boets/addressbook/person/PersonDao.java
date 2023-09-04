package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.SearchCriteria;

import java.util.List;
import java.util.Optional;

public interface PersonDao {

    List<Person> getPersons();
    Optional<Person> getPersonById(Integer id);
    void savePerson(Person person);
    boolean existsEmailForPerson(String email);
    void deletePerson(Integer id);
    boolean personExistsWithId(Integer id);
    void updatePerson(Person person);
    List<Person> search(SearchCriteria searchCriteria);


}
