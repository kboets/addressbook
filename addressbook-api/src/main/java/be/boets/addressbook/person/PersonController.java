package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.PersonDto;
import be.boets.addressbook.mapper.PersonMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/person")
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("all")
    public List<PersonDto> findAll() {
        return personMapper.toDtos(personService.findAll());
    }

    @GetMapping("{id}")
    public PersonDto findById(@PathVariable("id") Integer id) {
        return  personMapper.toDto(personService.findById(id));
    }

    @PostMapping
    public void registerPerson(@RequestBody PersonDto personDto) {
        personService.addPerson(personMapper.toModel(personDto));
    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") Integer id) {
        personService.deletePerson(id);
    }

    @PutMapping()
    public void updatePerson(@RequestBody PersonDto personDto) {
        personService.updatePerson(personMapper.toModel(personDto));
    }
}
