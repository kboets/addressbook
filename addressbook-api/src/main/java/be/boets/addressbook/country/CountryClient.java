package be.boets.addressbook.country;

import be.boets.addressbook.config.ClientProperties;
import be.boets.addressbook.domain.Country;
import be.boets.addressbook.exception.ClientException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.gson.JsonParser.parseString;

@Component
public class CountryClient {

    private final String version;
    private final ClientProperties clientProperties;
    private final HttpClient httpClient;

    public CountryClient(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        this.version = "/v3.1";
    }


    public List<Country> getCountries() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createAllCountryUrl()))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new ClientException(response.statusCode(), "retrieve of all countries failed");
        }
        return parseJson(response.body());
    }

    protected List<Country> parseJson(String jsonAsString) {
        List<Country> countries = new ArrayList<>();
        JsonElement jsonElement = parseString(jsonAsString);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<JsonElement> jsonElements = jsonArray.asList();
        for (JsonElement element : jsonElements) {
            JsonObject jsonObject = element.getAsJsonObject();
            // only european countries are taken
            Optional<String> regionValue = getValue("region", jsonObject);
            if (regionValue.isEmpty() || !regionValue.get().equalsIgnoreCase("Europe")) {
                continue;
            }
            Country country = new Country();
            // map name
            JsonObject nameObject = jsonObject.getAsJsonObject("name");
            Optional<String> nameValue = getValue("common", nameObject);
            nameValue.ifPresent(country::setName);
            // map code
            Optional<String> value = getValue("cca2", jsonObject);
            value.ifPresent(country::setCountryCode);
            // map phone code
            JsonObject phoneObject = jsonObject.getAsJsonObject("idd");
            String countryCodePhone = "";
            Optional<String> valuePhone1 = getValue("root", phoneObject);
            if (valuePhone1.isPresent()) {
                countryCodePhone = valuePhone1.get();
                countryCodePhone = StringUtils.remove(countryCodePhone, "+");
                countryCodePhone = "00" + countryCodePhone;
            }
            Optional<String> valuePhone2 = getValue("suffixes", phoneObject);
            if (valuePhone2.isPresent()) {
                country.setPhoneCode(countryCodePhone + valuePhone2.get());
            }
            countries.add(country);
        }
        return countries;

    }

    public Optional<String> getValue(String name, JsonObject jsonObject) {
        if (!jsonObject.get(name).isJsonNull()) {
            if (jsonObject.get(name).isJsonArray()) {
                JsonArray jsonArray = jsonObject.getAsJsonArray(name);
                if (!jsonArray.isEmpty()) {
                    return Optional.of(jsonArray.get(0).getAsString());
                }
            } else {
                return Optional.of(jsonObject.get(name).getAsString());
            }
        }
        return Optional.empty();
    }

    private String createAllCountryUrl() {
        return this.clientProperties.getCountryUrl() + version + "/all";
    }
}
