package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.FireDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link FireAlertService}.
 *
 * <p>Verifies that fire alerts are correctly generated for a given address,
 * including resident details, associated medical records, and fire station number.</p>
 */
@SpringBootTest
class FireAlertServiceTest {

    @MockitoBean
    private PersonService personService;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @MockitoBean
    private FireStationService fireStationService;

    @Autowired
    private FireAlertService fireAlertService;

    private List<Person> mockPersons;
    private List<MedicalRecord> mockMedicalRecords;
    private List<FireStation> mockFireStations;

    /**
     * Initializes mock data for persons, medical records, and fire stations before each test.
     */
    @BeforeEach
    void setUp() {
        mockPersons = List.of(
                new Person("John", "Doe", "123 Main St", "City", "11111", "111-222-3333", "john@doe.com"),
                new Person("Jane", "Doe", "123 Main St", "City", "11111", "111-222-3334", "jane@doe.com")
        );

        mockMedicalRecords = List.of(
                new MedicalRecord("John", "Doe", LocalDate.of(1990, 1, 1), List.of("med1"), List.of("pollen")),
                new MedicalRecord("Jane", "Doe", LocalDate.of(1985, 5, 20), List.of("med2", "med3"), List.of())
        );

        mockFireStations = List.of(
                new FireStation("123 Main St", "2"),
                new FireStation("456 Other St", "5")
        );
    }

    /**
     * Test retrieving fire alert information for an address with valid data.
     * Verifies resident details, medical records, and fire station number.
     */
    @Test
    void testGetFireAlertByAddress_ValidData() {
        when(personService.getAllPersons()).thenReturn(mockPersons);
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(mockMedicalRecords);
        when(fireStationService.getAllFireStations()).thenReturn(mockFireStations);

        FireDTO result = fireAlertService.getFireAlertByAddress("123 Main St");

        assertThat(result).isNotNull();
        assertThat(result.getFireStationNumber()).isEqualTo(2);
        assertThat(result.getResidents()).hasSize(2);

        var john = result.getResidents().stream()
                .filter(r -> r.getFirstName().equals("John"))
                .findFirst()
                .orElseThrow();
        assertThat(john.getAge()).isGreaterThan(30);
        assertThat(john.getMedications()).containsExactly("med1");
        assertThat(john.getAllergies()).containsExactly("pollen");

        var jane = result.getResidents().stream()
                .filter(r -> r.getFirstName().equals("Jane"))
                .findFirst()
                .orElseThrow();
        assertThat(jane.getMedications()).containsExactlyInAnyOrder("med2", "med3");
        assertThat(jane.getAllergies()).isEmpty();
    }

    /**
     * Test scenario where no fire station is associated with the address.
     * Fire station number should default to 0.
     */
    @Test
    void testGetFireAlertByAddress_NoFireStationFound() {
        when(personService.getAllPersons()).thenReturn(mockPersons);
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(mockMedicalRecords);
        when(fireStationService.getAllFireStations()).thenReturn(List.of());

        FireDTO result = fireAlertService.getFireAlertByAddress("123 Main St");

        assertThat(result).isNotNull();
        assertThat(result.getFireStationNumber()).isEqualTo(0);
        assertThat(result.getResidents()).hasSize(2);
    }

    /**
     * Test scenario where the fire station number is invalid (non-numeric).
     * Fire station number should default to 0.
     */
    @Test
    void testGetFireAlertByAddress_InvalidStationNumber() {
        when(personService.getAllPersons()).thenReturn(mockPersons);
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(mockMedicalRecords);
        when(fireStationService.getAllFireStations())
                .thenReturn(List.of(new FireStation("123 Main St", "invalid")));

        FireDTO result = fireAlertService.getFireAlertByAddress("123 Main St");

        assertThat(result).isNotNull();
        assertThat(result.getFireStationNumber()).isEqualTo(0);
    }

    /**
     * Test scenario where medical records are missing.
     * Resident ages and medical details should default to empty or zero values.
     */
    @Test
    void testGetFireAlertByAddress_NoMedicalRecord() {
        when(personService.getAllPersons()).thenReturn(mockPersons);
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(List.of());
        when(fireStationService.getAllFireStations()).thenReturn(mockFireStations);

        FireDTO result = fireAlertService.getFireAlertByAddress("123 Main St");

        assertThat(result.getResidents()).hasSize(2);
        assertThat(result.getResidents().get(0).getAge()).isEqualTo(0);
        assertThat(result.getResidents().get(0).getMedications()).isEmpty();
        assertThat(result.getResidents().get(0).getAllergies()).isEmpty();
    }
}
