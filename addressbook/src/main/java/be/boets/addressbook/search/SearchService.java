package be.boets.addressbook.search;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.SearchCriteria;
import be.boets.addressbook.person.PersonDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final PersonDao personDao;


    public SearchService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> search(SearchCriteria searchCriteria) {
        return personDao.search(searchCriteria);
    }
}
