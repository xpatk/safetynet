package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.FloodStationsDTO;
import com.safetynet.safetynet.dto.FloodStationsDTO.HouseholdInfo;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FloodAlertServiceTest {

    @MockitoBean
    private PersonService personService;

    @MockitoBean
    private FireStationService fireStationService;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private FloodAlertService floodAlertService;

    private List<Person> mockPersons;
    private List<MedicalRecord> mockMedicalRecords;
    private List<FireStation> mockFireStations;

    @BeforeEach
    void setUp() {
        // Fire stations
        mockFireStations = List.of(
                new FireStation("1 Dover St", "1"),
                new FireStation("2 Elm St", "2")
        );

        // Persons
        mockPersons = List.of(
                new Person("John", "Doe", "1 Dover St", "City", "11111", "123-456-7890", "john@doe.com"),
                new Person("Jane", "Doe", "1 Dover St", "City", "11111", "123-456-7891", "jane@doe.com"),
                new Person("Alice", "Smith", "2 Elm St", "City", "22222", "222-333-4444", "alice@smith.com")
        );

        // Medical records
        mockMedicalRecords = List.of(
                new MedicalRecord("John", "Doe", LocalDate.of(2015, 1, 1), List.of("med1"), List.of("allergy1")),
                new MedicalRecord("Jane", "Doe", LocalDate.of(1990, 1, 1), List.of("med2"), List.of("allergy2")),
                new MedicalRecord("Alice", "Smith", LocalDate.of(2005, 5, 5), List.of(), List.of())
        );

        // Mocking service calls
        when(fireStationService.getAllFireStations()).thenReturn(mockFireStations);
        when(personService.getAllPersons()).thenReturn(mockPersons);
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(mockMedicalRecords);
    }

    @Test
    void testGetHouseholdsByStations() {
        List<String> stations = List.of("1", "2");

        FloodStationsDTO result = floodAlertService.getHouseholdsByStations(stations);

        assertThat(result).isNotNull();
        Map<String, List<HouseholdInfo>> households = result.getHouseholds();

        // Check addresses
        assertThat(households.keySet()).containsExactlyInAnyOrder("1 Dover St", "2 Elm St");

        // Check persons at "1 Dover St"
        List<HouseholdInfo> doverHouse = households.get("1 Dover St");
        assertThat(doverHouse).hasSize(2);
        assertThat(doverHouse).anySatisfy(h -> assertThat(h.getFirstName()).isEqualTo("John"));
        assertThat(doverHouse).anySatisfy(h -> assertThat(h.getFirstName()).isEqualTo("Jane"));

        // Check ages calculated correctly
        HouseholdInfo john = doverHouse.stream().filter(h -> h.getFirstName().equals("John")).findFirst().orElse(null);
        assertThat(john).isNotNull();
        assertThat(john.getAge()).isLessThanOrEqualTo(18);

        // Check Alice
        List<HouseholdInfo> elmHouse = households.get("2 Elm St");
        assertThat(elmHouse).hasSize(1);
        assertThat(elmHouse.get(0).getFirstName()).isEqualTo("Alice");
    }
}
