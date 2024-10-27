package be.boets.addressbook;

import be.boets.addressbook.config.ClientProperties;
import be.boets.addressbook.domain.Address;
import be.boets.addressbook.domain.Country;
import be.boets.addressbook.domain.Person;
import be.boets.addressbook.person.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ClientProperties.class)
public class AddressbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressbookApplication.class, args);
	}

	//@Bean
//	CommandLineRunner runner(PersonRepository personRepository) {
//		return args -> {
//			Country belgium = Country.CountryBuilder.aCountry().withCountryCode("BE").withName("Belgie").build();
//			Address homeAddress = Address.AddressBuilder.anAddress().withStreet("Westelsebaan")
//					.withHouseNumber("5")
//					.withCity("Averbode")
//					.withZipCode("3271")
//					.withCountry(belgium)
//					.build();
//			LocalDate birthDateKurt = LocalDate.of(1974, Month.JANUARY, 16);
//			Person Kurt = Person.PersonBuilder.aPerson()
//					.withName("Boets").withFirstName("Kurt").withEmail("k.boets@gmail.com").withBirthDate(birthDateKurt)
//					.withMainAddress(homeAddress)
//					.build();
//			LocalDate birthDateEls = LocalDate.of(1971, Month.MAY, 7);
//			Person Els = Person.PersonBuilder.aPerson().withName("Aerts").withFirstName("Els").withEmail("els.aerts@gmail.com").withBirthDate(birthDateEls)
//					.withMainAddress(homeAddress)
//					.build();
//			List<Person> persons = List.of(Kurt, Els);
//			//personRepository.saveAll(persons);
//		};
//	}
}
