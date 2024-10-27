package be.boets.addressbook.dto;

import java.time.LocalDate;

public record SearchCriteria(String name, String firstName, LocalDate birthDate, String street, String number, String zipCode, String city) {
}
