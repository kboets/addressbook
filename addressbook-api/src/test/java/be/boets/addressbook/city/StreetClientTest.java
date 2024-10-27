package be.boets.addressbook.city;

import be.boets.addressbook.config.ClientProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class StreetClientTest {
    @Mock
    private ClientProperties clientProperties;
    @InjectMocks
    private StreetClient streetClient;

    @BeforeEach
    void setUp() {
        openMocks(this);
        when(clientProperties.getStreetUrl()).thenReturn("http://mock.api");
    }

    @Test
    public void retrieveStreet_givenValidZipCode_shouldReturnList()  {
        Set<String> retrievedStreets = streetClient.parseResponse(StreetClientMockData.getScherpenheuvelStreetData());
        assertNotNull(retrievedStreets);
        assertFalse(retrievedStreets.isEmpty());
    }

    @Test
    public void retrieveStreet_givenEmptyStringResponse_shouldReturnEmptyList()  {
        Set<String> retrievedStreets = streetClient.parseResponse("");
        assertNotNull(retrievedStreets);
        assertTrue(retrievedStreets.isEmpty());
    }

    @Test
    public void retrieveStreet_givenNullResponse_shouldReturnEmptyList()  {
        Set<String> retrievedStreets = streetClient.parseResponse(null);
        assertNotNull(retrievedStreets);
        assertTrue(retrievedStreets.isEmpty());
    }
}
