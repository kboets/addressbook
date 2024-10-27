package be.boets.addressbook.city;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StreetClientIntegrationTest {
    @Autowired
    private StreetClient streetClient;

    @Test
    public void retrieveCity_givenNeerpelt_shouldAllStreets() throws Exception {
        Set<String> retrievedStreets = streetClient.retrieveStreets("3910");
        assertFalse(retrievedStreets.isEmpty());
        assertTrue(retrievedStreets.contains("Kaulillerweg"));
    }
}
