package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.PersonDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("all")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("{id}")
    public Person findById(@PathVariable("id") Integer id) {
        return  personService.findById(id);
    }

    @PostMapping
    public void registerPerson(@RequestBody PersonDto personDto) {
        personService.addPerson(personDto);
    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") Integer id) {
        personService.deletePerson(id);
    }

    @PutMapping("{id}")
    public void updatePerson(@PathVariable("id") Integer id, PersonDto personDto) {
        personService.updatePerson(id, personDto);
    }
}
