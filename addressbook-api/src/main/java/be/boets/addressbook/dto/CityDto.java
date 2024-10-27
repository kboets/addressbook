package be.boets.addressbook.dto;

import java.util.Set;

public record CityDto(
        Integer id, String name,String zipCode, Set<String> streetNames, String countryCode) {
}
