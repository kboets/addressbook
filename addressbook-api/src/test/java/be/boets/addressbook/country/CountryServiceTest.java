package be.boets.addressbook.country;

import be.boets.addressbook.domain.Country;
import be.boets.addressbook.dto.CountryDto;
import be.boets.addressbook.mapper.CountryMapper;
import be.boets.addressbook.mapper.CountryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    private CountryService underTest;
    @Mock
    private CountryJpaRepository countryRepository;
    @Mock
    private CountryClient countryClient;
    private CountryMapper countryMapper;
    private List<Country> expectedCountries;

    @BeforeEach
    void setUp() {
        countryMapper = new CountryMapperImpl();
        underTest = new CountryService(countryRepository, countryClient, countryMapper);
        expectedCountries = new ArrayList<>();
    }

    @Test
    void isInitDataLoaded_whenCountriesExist_shouldReturnTrue() {
        // given
        expectedCountries.add(createCountry());
        
        // when
        Mockito.when(countryRepository.findAll()).thenReturn(expectedCountries);
        
        // then
        boolean result = underTest.isInitDataLoaded();
        assertThat(result).isTrue();
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void isInitDataLoaded_whenNoCountriesExist_shouldReturnFalse() {
        // given
        List<Country> emptyList = new ArrayList<>();
        
        // when
        Mockito.when(countryRepository.findAll()).thenReturn(emptyList);
        
        // then
        boolean result = underTest.isInitDataLoaded();
        assertThat(result).isFalse();
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void loadAllCountries_shouldSaveCountriesAndReturnTrue() throws Exception {
        // given
        expectedCountries.add(createCountry());
        
        // when
        Mockito.when(countryClient.getCountries()).thenReturn(expectedCountries);
        Mockito.when(countryRepository.findAll()).thenReturn(expectedCountries);
        
        // then
        boolean result = underTest.loadAllCountries();
        assertThat(result).isTrue();
        verify(countryClient, times(1)).getCountries();
        verify(countryRepository, times(1)).saveAll(expectedCountries);
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void getAllCountries_shouldReturnAllCountriesAsDto() {
        // given
        expectedCountries.add(createCountry());
        
        // when
        Mockito.when(countryRepository.findAll(Mockito.any(org.springframework.data.domain.Sort.class)))
                .thenReturn(expectedCountries);


        // then
        List<CountryDto> result = underTest.getAllCountries();
        List<CountryDto> expected = countryMapper.toDtos(expectedCountries);
        assertThat(result).isEqualTo(expected);
        verify(countryRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    private Country createCountry() {
        return Country.CountryBuilder.aCountry()
                .withId(1)
                .withName("Belgium")
                .withCountryCode("BE")
                .withPhoneCode("+32")
                .build();
    }
}
