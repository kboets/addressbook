package be.boets.addressbook.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * https://restcountries.com/#endpoints-all
 */
@Entity
@Table(
        name = "country",
        schema = "addressbook"
)
public class Country implements Serializable  {

    @Id
    @SequenceGenerator(
            name = "country_id_seq",
            sequenceName = "country_id_seq",
            schema = "addressbook",
            allocationSize =  1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "country_id_seq")
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String countryCode;
    @Column(nullable = false)
    private String phoneCode;

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) && Objects.equals(name, country.name) && Objects.equals(countryCode, country.countryCode) && Objects.equals(phoneCode, country.phoneCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryCode, phoneCode);
    }


    public static final class CountryBuilder {
        private Integer id;
        private String name;
        private String countryCode;
        private String phoneCode;

        private CountryBuilder() {
        }

        public static CountryBuilder aCountry() {
            return new CountryBuilder();
        }

        public CountryBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public CountryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CountryBuilder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CountryBuilder withPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
            return this;
        }

        public Country build() {
            Country country = new Country();
            country.setId(id);
            country.setName(name);
            country.setCountryCode(countryCode);
            country.setPhoneCode(phoneCode);
            return country;
        }
    }
}
