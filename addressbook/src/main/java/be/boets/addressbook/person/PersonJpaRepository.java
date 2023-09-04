package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.SearchCriteria;
import be.boets.addressbook.search.SearchSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonJpaRepository implements PersonDao {

    private final PersonRepository personRepository;

    public PersonJpaRepository(PersonRepository repository) {
        this.personRepository = repository;
    }

    @Override
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Integer id) {
        return personRepository.findById(id);
    }

    @Override
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public boolean existsEmailForPerson(String email) {
        return personRepository.existsPersonByEmail(email);
    }

    @Override
    public void deletePerson(Integer id) {
        personRepository.deleteById(id);
    }

    @Override
    public boolean personExistsWithId(Integer id) {
        return personRepository.existsPersonById(id);
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public List<Person> search(SearchCriteria searchCriteria) {
        Specification<Person> spec = SearchSpecs.searchByCriteria(searchCriteria);
        return personRepository.findAll(spec);
    }
}
