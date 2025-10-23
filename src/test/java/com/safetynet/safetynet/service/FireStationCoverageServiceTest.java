package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.FireStationCoverageDTO;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class for FireStationCoverageService.
 */
@SpringBootTest
public class FireStationCoverageServiceTest {

    @MockitoBean
    private FireStationService fireStationService;

    @MockitoBean
    private PersonService personService;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private FireStationCoverageService fireStationCoverageService;

    private List<Person> mockPersons;
    private List<MedicalRecord> mockMedicalRecords;

    @BeforeEach
    void setUp() {
        mockPersons = List.of(
                new Person("Alice", "Smith", "10 Elm St", "CityA", "11111", "111-222-3333", "alice@example.com"),
                new Person("Bob", "Smith", "10 Elm St", "CityA", "11111", "111-222-3334", "bob@example.com")
        );

        mockMedicalRecords = List.of(
                new MedicalRecord("Alice", "Smith", LocalDate.of(2010, 1, 1), List.of(), List.of()),
                new MedicalRecord("Bob", "Smith", LocalDate.of(1990, 1, 1), List.of(), List.of())
        );
    }

    @Test
    void testGetCoverageByStation() {
        // GIVEN
        when(fireStationService.getAllFireStations()).thenReturn(List.of(
                new com.safetynet.safetynet.model.FireStation("10 Elm St", "1")
        ));
        when(personService.getAllPersons()).thenReturn(mockPersons);
        when(medicalRecordService.findByName("Alice", "Smith")).thenReturn(mockMedicalRecords.get(0));
        when(medicalRecordService.findByName("Bob", "Smith")).thenReturn(mockMedicalRecords.get(1));

        // WHEN
        FireStationCoverageDTO dto = fireStationCoverageService.getCoverageByStation("1");

        // THEN
        assertThat(dto).isNotNull();
        assertThat(dto.getPersons()).hasSize(2);
        assertThat(dto.getChildCount()).isEqualTo(1);
        assertThat(dto.getAdultCount()).isEqualTo(1);
        assertThat(dto.getPersons().get(0).getFirstName()).isEqualTo("Alice");
        assertThat(dto.getPersons().get(1).getFirstName()).isEqualTo("Bob");
    }
}
