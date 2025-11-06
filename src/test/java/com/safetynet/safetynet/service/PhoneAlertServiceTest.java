package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.PhoneAlertDTO;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PhoneAlertService}.
 *
 * <p>Verifies that the service correctly retrieves phone numbers of residents
 * covered by a given fire station and handles multiple scenarios including
 * multiple residents at the same address and no residents found.</p>
 */
@SpringBootTest
public class PhoneAlertServiceTest {

    @MockitoBean
    private FireStationService fireStationService;

    @MockitoBean
    private PersonService personService;

    @Autowired
    private PhoneAlertService phoneAlertService;

    private FireStation station1;
    private Person johnDoe;
    private Person janeDoe;

    /**
     * Initializes mock data before each test.
     */
    @BeforeEach
    void setUp() {
        station1 = new FireStation("123 Main St", "1");
        johnDoe = new Person("John", "Doe", "123 Main St", "City", "11111", "john@doe.com", "123-456-7890");
        janeDoe = new Person("Jane", "Doe", "456 Elm St", "City", "11111", "jane@doe.com", "987-654-3210");
    }

    @Test
    void testGetPhonesByFireStation_WithResidents() {
        // GIVEN: Fire station exists and one resident lives at its address
        when(fireStationService.getAllFireStations()).thenReturn(List.of(station1));
        when(personService.getAllPersons()).thenReturn(List.of(johnDoe, janeDoe));

        // WHEN
        PhoneAlertDTO result = phoneAlertService.getPhonesByFireStation("1");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getPhoneNumbers()).hasSize(1);
        assertThat(result.getPhoneNumbers()).contains("123-456-7890");

        verify(fireStationService, times(1)).getAllFireStations();
        verify(personService, times(1)).getAllPersons();
    }

    @Test
    void testGetPhonesByFireStation_NoResidents() {
        // GIVEN: No fire stations exist for the requested number
        when(fireStationService.getAllFireStations()).thenReturn(List.of());
        when(personService.getAllPersons()).thenReturn(List.of(johnDoe, janeDoe));

        // WHEN
        PhoneAlertDTO result = phoneAlertService.getPhonesByFireStation("1");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getPhoneNumbers()).isEmpty();

        verify(fireStationService, times(1)).getAllFireStations();
        verify(personService, times(1)).getAllPersons();
    }

    @Test
    void testGetPhonesByFireStation_MultipleResidentsSameAddress() {
        // GIVEN: Multiple residents live at the same fire station address
        Person janeAtSameAddress = new Person("Jane", "Doe", "123 Main St", "City", "11111", "jane@doe.com", "987-654-3210");
        when(fireStationService.getAllFireStations()).thenReturn(List.of(station1));
        when(personService.getAllPersons()).thenReturn(List.of(johnDoe, janeAtSameAddress));

        // WHEN
        PhoneAlertDTO result = phoneAlertService.getPhonesByFireStation("1");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getPhoneNumbers()).hasSize(2);
        assertThat(result.getPhoneNumbers()).containsExactlyInAnyOrder("123-456-7890", "987-654-3210");

        verify(fireStationService, times(1)).getAllFireStations();
        verify(personService, times(1)).getAllPersons();
    }
}
