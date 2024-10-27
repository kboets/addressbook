package be.boets.addressbook.mapper;

import be.boets.addressbook.domain.Address;
import be.boets.addressbook.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = CountryMapper.class)
public interface AddressMapper {
    @Mappings({
            @Mapping(target = "countryDto", source = "country")
    })
    AddressDto toDto(Address address);

    @Mappings({
            @Mapping(target = "country", source = "countryDto")
    })
    Address toModel(AddressDto countryDto);

    List<AddressDto> toDtos(List<Address> addresses);

    List<Address> toModels(List<AddressDto> addressDtos);
}
