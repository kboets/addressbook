package be.boets.addressbook.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "address",
        schema = "addressbook"
)
public class Address implements Serializable {

    @Id
    @SequenceGenerator(
            name = "address_id_seq",
            sequenceName = "address_id_seq",
            schema = "addressbook",
            allocationSize =  1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String houseNumber;

    private String box;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToMany(
            mappedBy = "mainAddress"
    )
    private List<Person> residents;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Person> getResidents() {
        return residents;
    }

    public void setResidents(List<Person> residents) {
        this.residents = residents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && Objects.equals(box, address.box) && Objects.equals(zipCode, address.zipCode) && Objects.equals(city, address.city) && Objects.equals(country, address.country) && Objects.equals(residents, address.residents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, houseNumber, box, zipCode, city, country, residents);
    }


    public static final class AddressBuilder {
        private Integer id;
        private String street;
        private String houseNumber;
        private String box;
        private String zipCode;
        private String city;
        private Country country;
        private List<Person> residents;

        private AddressBuilder() {
        }

        public static AddressBuilder anAddress() {
            return new AddressBuilder();
        }

        public AddressBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public AddressBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public AddressBuilder withBox(String box) {
            this.box = box;
            return this;
        }

        public AddressBuilder withZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public AddressBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder withCountry(Country country) {
            this.country = country;
            return this;
        }

        public AddressBuilder withResidents(List<Person> residents) {
            this.residents = residents;
            return this;
        }

        public Address build() {
            Address address = new Address();
            address.setStreet(street);
            address.setHouseNumber(houseNumber);
            address.setBox(box);
            address.setZipCode(zipCode);
            address.setCity(city);
            address.setCountry(country);
            address.setResidents(residents);
            address.id = this.id;
            return address;
        }
    }
}
