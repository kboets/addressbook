package be.boets.addressbook.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "city",
        schema = "addressbook")
public class City implements Serializable {

    @Id
    @SequenceGenerator(
            name = "city_id_seq",
            sequenceName = "city_id_seq",
            schema = "addressbook",
            allocationSize =  1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "city_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String zipCode;

    @ElementCollection
    @CollectionTable(name = "street_names", schema = "addressbook", joinColumns = @JoinColumn(name = "city_id"))
    @Column(name = "name")
    private Set<String> streetNames;

    @Column(nullable = false)
    private String countryCode;


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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Set<String> getStreetNames() {
        if(streetNames == null) {
            streetNames = new HashSet<>();
        }
        return streetNames;
    }

    public void setStreetNames(Set<String> streetNames) {
        this.streetNames = streetNames;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) && Objects.equals(name, city.name) && Objects.equals(zipCode, city.zipCode) && Objects.equals(streetNames, city.streetNames) && Objects.equals(countryCode, city.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, zipCode, streetNames, countryCode);
    }


    public static final class CityBuilder {
        private Integer id;
        private String name;
        private String zipCode;
        private Set<String> streetNames;
        private String countryCode;

        private CityBuilder() {
        }

        public static CityBuilder aCity() {
            return new CityBuilder();
        }

        public CityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public CityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CityBuilder withZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public CityBuilder withStreetNames(Set<String> streetNames) {
            this.streetNames = streetNames;
            return this;
        }

        public CityBuilder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public City build() {
            City city = new City();
            city.setId(id);
            city.setName(name);
            city.setZipCode(zipCode);
            city.setStreetNames(streetNames);
            city.setCountryCode(countryCode);
            return city;
        }
    }
}
