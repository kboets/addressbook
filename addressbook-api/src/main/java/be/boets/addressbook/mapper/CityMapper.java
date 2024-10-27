package be.boets.addressbook.mapper;

import be.boets.addressbook.domain.City;
import be.boets.addressbook.dto.CityDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDto toDto (City city);
    City toModel(CityDto cityDto);
    List<CityDto> toDtos(List<City> countries);
    List<City> toModels(List<CityDto> countries);
}
