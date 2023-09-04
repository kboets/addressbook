package be.boets.addressbook.dto;

import java.time.LocalDate;

public record PersonDto(
        Integer id, String name, String firstName, LocalDate birthDate, String phoneNumber, String mobilePhone, String email, AddressDto mainAddress) {
}
