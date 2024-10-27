package be.boets.addressbook.city;

import be.boets.addressbook.domain.City;
import be.boets.addressbook.dto.CityDto;
import be.boets.addressbook.mapper.CityMapper;
import be.boets.addressbook.mapper.CityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    private CityService underTest;
    @Mock
    private CityJpaRepository cityJpaRepository;
    @Mock
    private CityClient cityClient;
    @Mock
    private StreetClient streetClient;
    private CityMapper cityMapper;
    private Set<String> streetNames;
    private String zipCode;
    private List<City> expectedCities;


    @BeforeEach
    void setUp() {
        cityMapper = new CityMapperImpl();
        underTest = new CityService(cityJpaRepository, cityClient, streetClient, cityMapper);
        streetNames = new HashSet<>();
        streetNames.add("Kaulillerweg");
        streetNames.add("Peerderbaan");
        zipCode = "3910";
        expectedCities = new ArrayList<>();
    }

    @Test
    void getCity_givingBelgianZipCode_shouldCallStreetClient() throws Exception {
        // given
        City expectedCity = createBelgianCity();
        expectedCities.add(expectedCity);
        // when
        Mockito.when(cityJpaRepository.findByZipCode(zipCode)).thenReturn(expectedCities);
        Mockito.when(streetClient.retrieveStreets(expectedCity.getZipCode())).thenReturn(streetNames);
        Mockito.when(cityJpaRepository.save(expectedCity)).thenReturn(expectedCity);
        // then
        List<CityDto> actual = underTest.getCity(zipCode);
        List<CityDto> expected = cityMapper.toDtos(expectedCities);
        assertThat(actual).isEqualTo(expected);
        verify(cityJpaRepository,  (times(1))).findByZipCode(zipCode);
        verify(streetClient, times(1)).retrieveStreets(expectedCity.getZipCode());
        verify(cityJpaRepository, times(1)).save(expectedCity);
    }

    @Test
    void getCity_givingOtherZipCode_shouldNotCallStreetClient() throws Exception {
        City otherCity = City.CityBuilder.aCity().withCountryCode("NL").withName("Eindhoven").withZipCode("EI 5855").build();
        expectedCities.add(otherCity);
        // when
        Mockito.when(cityJpaRepository.findByZipCode(otherCity.getZipCode())).thenReturn(expectedCities);
        // then
        List<CityDto> actual = underTest.getCity(otherCity.getZipCode());
        List<CityDto> expected = cityMapper.toDtos(expectedCities);
        assertThat(actual).isEqualTo(expected);
        verify(cityJpaRepository,  (times(1))).findByZipCode(otherCity.getZipCode());
        verify(streetClient, times(0)).retrieveStreets(otherCity.getZipCode());
        verify(cityJpaRepository, times(0)).save(otherCity);
    }


    private City createBelgianCity() {
        return City.CityBuilder.aCity()
                .withCountryCode("BE")
                .withName("Pelt")
                .withZipCode("3271")
                .build();
    }

}
