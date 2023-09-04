package be.boets.addressbook.mapper;

import be.boets.addressbook.domain.Country;
import be.boets.addressbook.dto.CountryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto toDto (Country country);
    Country toModel(CountryDto countryDto);
    List<CountryDto> toDtos(List<Country> countries);
    List<Country> toModels(List<CountryDto> countries);

}
