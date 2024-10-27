package be.boets.addressbook.person;

import be.boets.addressbook.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {

    boolean existsPersonByEmail(String email);
    boolean existsPersonById(Integer id);
}
