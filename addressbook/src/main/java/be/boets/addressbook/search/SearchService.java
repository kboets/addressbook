package be.boets.addressbook.search;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.SearchCriteria;
import be.boets.addressbook.dto.PersonDto;
import be.boets.addressbook.mapper.PersonMapper;
import be.boets.addressbook.person.PersonDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final PersonDao personDao;
    private final PersonMapper personMapper;

    public SearchService(PersonDao personDao, PersonMapper personMapper) {
        this.personDao = personDao;
        this.personMapper = personMapper;
    }

    public List<PersonDto> search(SearchCriteria searchCriteria) {
        List<Person> persons = personDao.search(searchCriteria);
        return personMapper.toDtos(persons);
    }
}
