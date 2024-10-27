package be.boets.addressbook.country;

import be.boets.addressbook.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryJpaRepository extends JpaRepository<Country, Integer> {
}
