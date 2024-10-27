package be.boets.addressbook.city;

import be.boets.addressbook.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityJpaRepository extends JpaRepository<City, Integer> {
    List<City> findByZipCode(String zipCode);
}

