package be.boets.addressbook.city;

import be.boets.addressbook.config.ClientProperties;
import be.boets.addressbook.domain.City;
import be.boets.addressbook.exception.ClientException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static com.google.gson.JsonParser.parseString;

@Component
public class CityClient {

    private final HttpClient httpClient;
    private final ClientProperties clientProperties;
    private final String countryBECode;

    public CityClient(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        this.countryBECode = "BE";
    }

    public Set<City> retrieveCity(String zipCode) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createCityUrl(zipCode)))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new ClientException(response.statusCode(), "retrieve of city failed");
        }
        return parseResponse(response.body(), zipCode);
    }

    protected Set<City> parseResponse(String jsonAsString, String zipCode) {
        JsonElement jsonElement = parseString(jsonAsString);
        Set<City> cities = new HashSet<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            JsonObject postCodeObject = jsonObject.get("Postcode").getAsJsonObject();
            String mainCityZipCode = postCodeObject.get("postcode_hoofdgemeente").getAsString();
            City city = new City();
            city.setZipCode(zipCode);
            city.setCountryCode(countryBECode);
            if (mainCityZipCode.equalsIgnoreCase(zipCode)) {
                city.setName(postCodeObject.get("naam_hoofdgemeente").getAsString());
                cities.add(city);
            } else {
                city.setName(postCodeObject.get("naam_deelgemeente").getAsString());
                cities.add(city);
            }
        }
        return cities;


    }

    private String createCityUrl(String zipCode) {
        return clientProperties.getCityUrl() + "/" + zipCode + ".json";
    }
}
