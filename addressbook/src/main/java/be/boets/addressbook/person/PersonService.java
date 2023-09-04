package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.PersonDto;
import be.boets.addressbook.exception.DuplicateResourceException;
import be.boets.addressbook.exception.ResourceNotFoundException;
import be.boets.addressbook.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonDao personDao;
    private final PersonMapper personMapper;

    public PersonService(PersonDao personDao, PersonMapper personMapper) {
        this.personDao = personDao;
        this.personMapper = personMapper;
    }

    public List<Person> findAll() {
        return personDao.getPersons();
    }

    public Person findById(Integer id) {
        Optional<Person> optionalPerson = personDao.getPersonById(id);
        return optionalPerson.orElseThrow(() -> new ResourceNotFoundException("Could not find a person with id %s".formatted(id)));
    }

    public void addPerson(PersonDto personDto) {
        // check if email exists
        if (personDao.existsEmailForPerson(personDto.email())) {
            throw new DuplicateResourceException("Email already taken");
        }
        Person person = personMapper.toModel(personDto);
        personDao.savePerson(person);

    }
    public void updatePerson(Integer id, PersonDto personDto) {
        Person person = personMapper.toModel(personDto);
        personDao.updatePerson(person);
    }

    public void deletePerson(Integer id) {
        if (!personDao.personExistsWithId(id)) {
            throw new ResourceNotFoundException("Could not find a person with id %s".formatted(id));
        }
        personDao.deletePerson(id);
    }
}
