package be.boets.addressbook.dto;

import java.time.LocalDate;

public record PersonDto(
        Integer id, String name, String firstName, LocalDate birthDate, String phoneNumber, String mobilePhone, String email, AddressDto mainAddress) {

    public static final class PersonDtoBuilder {
        private Integer id;
        private String name;
        private String firstName;
        private LocalDate birthDate;
        private String phoneNumber;
        private String mobilePhone;
        private String email;
        private AddressDto mainAddress;

        private PersonDtoBuilder() {
        }

        public static PersonDtoBuilder aPersonDto() {
            return new PersonDtoBuilder();
        }

        public PersonDtoBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public PersonDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PersonDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonDtoBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonDtoBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PersonDtoBuilder withMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
            return this;
        }

        public PersonDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public PersonDtoBuilder withMainAddress(AddressDto mainAddress) {
            this.mainAddress = mainAddress;
            return this;
        }

        public PersonDto build() {
            return new PersonDto(id, name, firstName, birthDate, phoneNumber, mobilePhone, email, mainAddress);
        }
    }
}
