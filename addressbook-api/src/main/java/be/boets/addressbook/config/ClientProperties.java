package be.boets.addressbook.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client")
public class ClientProperties {

    private String countryUrl;
    private String cityUrl;
    private String streetUrl;

    public String getCountryUrl() {
        return countryUrl;
    }

    public void setCountryUrl(String countryUrl) {
        this.countryUrl = countryUrl;
    }

    public String getCityUrl() {
        return cityUrl;
    }

    public void setCityUrl(String cityUrl) {
        this.cityUrl = cityUrl;
    }

    public void setStreetUrl(String streetUrl) {
        this.streetUrl = streetUrl;
    }

    public String getStreetUrl() {
        return streetUrl;
    }
}
