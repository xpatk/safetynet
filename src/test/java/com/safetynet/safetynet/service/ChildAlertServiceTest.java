package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.ChildAlertDTO;
import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ChildAlertService}.
 * Verifies detection of children and household members for a given address.
 */
@SpringBootTest
public class ChildAlertServiceTest {

    @MockBean
    private PersonService personService;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ChildAlertService childAlertService;

    private List<Person> mockPersons;
    private List<MedicalRecord> mockMedicalRecords;

    @BeforeEach
    void setUpForTest() {
        mockPersons = List.of(
                new Person("John", "Doe", "1 Dover St", "Miami", "11111", "122-333-4444", "john@doe.com"),
                new Person("Jane", "Doe", "1 Dover St", "Miami", "11111", "122-333-4444", "jane@doe.com")
        );

        mockMedicalRecords = List.of(
                new MedicalRecord("John", "Doe", LocalDate.of(2015, 1, 1), List.of("medication1"), List.of("allergy1")),
                new MedicalRecord("Jane", "Doe", LocalDate.of(1990, 1, 1), List.of("medication2"), List.of("allergy2"))
        );
    }

    @Test
    void testGetChildrenAtAddress_WithChildrenPresent() {
        // GIVEN
        when(personService.getAllPersons()).thenReturn(mockPersons);
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(mockMedicalRecords);

        // WHEN
        ChildAlertDTO result = childAlertService.getChildrenAtAddress("1 Dover St");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getChildren()).hasSize(1);
        assertThat(result.getChildren().get(0).getFirstName()).isEqualTo("John");
        assertThat(result.getChildren().get(0).getAge()).isLessThanOrEqualTo(18);

        assertThat(result.getHouseholdMembers()).hasSize(2);
        assertThat(result.getHouseholdMembers().stream()
                .anyMatch(m -> m.getFirstName().equals("Jane"))).isTrue();

        verify(personService, times(1)).getAllPersons();
        verify(medicalRecordService, times(1)).getAllMedicalRecords();
    }

    @Test
    void testGetChildrenAtAddress_NoChildrenFound() {
        // GIVEN - only adults
        mockMedicalRecords = List.of(
                new MedicalRecord("John", "Doe", LocalDate.of(1985, 1, 1), List.of(), List.of()),
                new MedicalRecord("Jane", "Doe", LocalDate.of(1990, 1, 1), List.of(), List.of())
        );
        when(personService.getAllPersons()).thenReturn(mockPersons);
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(mockMedicalRecords);

        // WHEN
        ChildAlertDTO result = childAlertService.getChildrenAtAddress("1 Dover St");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getChildren()).isEmpty();
        assertThat(result.getHouseholdMembers()).isEmpty();
    }

    @Test
    void testGetChildrenAtAddress_UnknownAddress() {
        // GIVEN
        when(personService.getAllPersons()).thenReturn(List.of());
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(List.of());

        // WHEN
        ChildAlertDTO result = childAlertService.getChildrenAtAddress("999 Nowhere Rd");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getChildren()).isEmpty();
        assertThat(result.getHouseholdMembers()).isEmpty();
    }

    @Test
    void testCalculateAge_ShouldReturnValidAge() {
        int age = childAlertService.calculateAge(LocalDate.now().minusYears(20));
        assertThat(age).isEqualTo(20);
    }

    @Test
    void testCalculateAge_ShouldReturnZeroWhenNull() {
        int age = childAlertService.calculateAge(null);
        assertThat(age).isEqualTo(0);
    }
}
