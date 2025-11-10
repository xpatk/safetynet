package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.PhoneAlertDTO;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PhoneAlertService}.
 *
 * <p>Verifies that the service correctly retrieves phone numbers of residents
 * covered by a given fire station and handles different scenarios:</p>
 * <ul>
 *     <li>Single resident at a station address</li>
 *     <li>No residents at the station</li>
 *     <li>Multiple residents at the same station address</li>
 * </ul>
 */
class PhoneAlertServiceTest {

    @Mock
    private FireStationService fireStationService;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    private FireStation station1;
    private Person johnDoe;
    private Person janeDoe;

    /**
     * Initializes mock data and opens Mockito annotations before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        station1 = new FireStation("123 Main St", "1");
        johnDoe = new Person("John", "Doe", "123 Main St", "City", "11111", "123-456-7890", "john@doe.com");
        janeDoe = new Person("Jane", "Doe", "456 Elm St", "City", "11111", "987-654-3210", "jane@doe.com");


    }

    /**
     * Tests retrieving phone numbers for a fire station when there is a single resident at the station address.
     */
    @Test
    void testGetPhonesByFireStation_WithResidents() {
        when(fireStationService.getAllFireStations()).thenReturn(List.of(station1));
        when(personService.getAllPersons()).thenReturn(List.of(johnDoe, janeDoe));

        PhoneAlertDTO result = phoneAlertService.getPhonesByFireStation("1");

        assertThat(result.getPhoneNumbers()).containsExactly("123-456-7890");
        verify(fireStationService).getAllFireStations();
        verify(personService).getAllPersons();
    }

    /**
     * Tests retrieving phone numbers for a fire station when no residents live at its addresses.
     */
    @Test
    void testGetPhonesByFireStation_NoResidents() {
        when(fireStationService.getAllFireStations()).thenReturn(List.of());
        when(personService.getAllPersons()).thenReturn(List.of(johnDoe, janeDoe));

        PhoneAlertDTO result = phoneAlertService.getPhonesByFireStation("1");

        assertThat(result.getPhoneNumbers()).isEmpty();
    }

    /**
     * Tests retrieving phone numbers for a fire station when multiple residents live at the same address.
     */
    @Test
    void testGetPhonesByFireStation_MultipleResidentsSameAddress() {
        Person janeAtSameAddress = new Person("Jane", "Doe", "123 Main St", "City", "11111", "987-654-3210", "jane@doe.com");
        when(fireStationService.getAllFireStations()).thenReturn(List.of(station1));
        when(personService.getAllPersons()).thenReturn(List.of(johnDoe, janeAtSameAddress));

        PhoneAlertDTO result = phoneAlertService.getPhonesByFireStation("1");

        assertThat(result.getPhoneNumbers())
                .containsExactlyInAnyOrder("123-456-7890", "987-654-3210");
    }
}
