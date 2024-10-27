package be.boets.addressbook.city;

import be.boets.addressbook.dto.CityDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/all")
    public List<CityDto> getCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/{zipCode}")
    public List<CityDto> getCityByZipCode(@PathVariable("zipCode") String zipCode) {
        return cityService.getCity(zipCode);
    }
}
