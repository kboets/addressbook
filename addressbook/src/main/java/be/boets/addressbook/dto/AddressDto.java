package be.boets.addressbook.dto;

public record AddressDto(
        Integer id, String street, String houseNumber, String box, String zipCode, String city, CountryDto countryDto) {
}
