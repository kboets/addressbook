package be.boets.addressbook.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "person",
        schema = "addressbook",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "person_email_unique",
                        columnNames = "email"
                )
        }
)
public class Person implements Serializable  {
    @Id
    @SequenceGenerator(
            name = "person_id_seq",
            sequenceName = "person_id_seq",
            schema = "addressbook",
            allocationSize =  1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private LocalDate birthDate;
    private String phoneNumber;
    private String mobilePhone;
    @Column(nullable = false)
    private String email;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "main_address_id", referencedColumnName = "id")
    private Address mainAddress;

    public Person() {
    }

    public Person(String name, String firstName, LocalDate birthDate, String phoneNumber, String mobilePhone, String email) {
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.mobilePhone = mobilePhone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(firstName, person.firstName) && Objects.equals(birthDate, person.birthDate) && Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(mobilePhone, person.mobilePhone) && Objects.equals(email, person.email) && Objects.equals(mainAddress, person.mainAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName, birthDate, phoneNumber, mobilePhone, email, mainAddress);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email=" + email +
                ", mainAddress=" + mainAddress +
                '}';
    }


    public static final class PersonBuilder {
        private Integer id;
        private String name;
        private String firstName;
        private LocalDate birthDate;
        private String phoneNumber;
        private String mobilePhone;
        private String email;
        private Address mainAddress;

        private PersonBuilder() {
        }

        public static PersonBuilder aPerson() {
            return new PersonBuilder();
        }

        public PersonBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public PersonBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PersonBuilder withMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
            return this;
        }

        public PersonBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public PersonBuilder withMainAddress(Address mainAddress) {
            this.mainAddress = mainAddress;
            return this;
        }


        public Person build() {
            Person person = new Person();
            person.setId(id);
            person.setName(name);
            person.setFirstName(firstName);
            person.setBirthDate(birthDate);
            person.setPhoneNumber(phoneNumber);
            person.setMobilePhone(mobilePhone);
            person.setEmail(email);
            person.setMainAddress(mainAddress);
            return person;
        }
    }
}
