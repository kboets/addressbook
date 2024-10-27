package be.boets.addressbook.city;

import be.boets.addressbook.config.ClientProperties;
import be.boets.addressbook.exception.ClientException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
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

/**
 * Retrieving street info for each belgian city using following url:
 * https://www.schoolvoorbeeld.be/geocrowl/streets.php?postalcode=3270&street=&city=&search=1&output=json
 */
@Component
public class StreetClient {
    private final HttpClient httpClient;
    private final ClientProperties clientProperties;

    public StreetClient(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }

    public Set<String> retrieveStreets(String zipCode) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createStreetUrl(zipCode)))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new ClientException(response.statusCode(), "retrieve of street failed");
        }
        return parseResponse(response.body());

    }

    protected Set<String> parseResponse(String jsonAsString) {
        Set<String> streets = new HashSet<>();
        if (!StringUtils.isEmpty(jsonAsString)) {
            JsonElement jsonElement = parseString(jsonAsString);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                streets.add(element.getAsString());
            }
        }
        return streets;
    }

    private String createStreetUrl(String zipCode) {
        return clientProperties.getStreetUrl()+ "/geocrowl/streets.php?postalcode="+zipCode+"&street=&city=&search=1&output=json";
    }

}

