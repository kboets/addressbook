package be.boets.addressbook.mapper;

import be.boets.addressbook.domain.Person;
import be.boets.addressbook.dto.PersonDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface PersonMapper {
    PersonDto toDto (Person person);
    Person toModel (PersonDto personDto);
    List<PersonDto> toDtos (List<Person> person);
    List<Person> toModels (List<PersonDto> personDtos);
}
