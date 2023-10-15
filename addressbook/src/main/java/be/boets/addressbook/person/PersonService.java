package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.exception.DuplicateResourceException;
import be.boets.addressbook.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> findAll() {
        return personDao.getPersons();
    }

    public Person findById(Integer id) {
        Optional<Person> optionalPerson = personDao.getPersonById(id);
        return optionalPerson.orElseThrow(() -> new ResourceNotFoundException("Could not find a person with id %s".formatted(id)));
    }

    public void addPerson(Person person) {
        // check if email exists
        if (personDao.existsEmailForPerson(person.getEmail())) {
            throw new DuplicateResourceException("Email already taken");
        }
        personDao.savePerson(person);

    }
    public void updatePerson(Person person) {
        personDao.updatePerson(person);
    }

    public void deletePerson(Integer id) {
        if (!personDao.personExistsWithId(id)) {
            throw new ResourceNotFoundException("Could not find a person with id %s".formatted(id));
        }
        personDao.deletePerson(id);
    }
}
