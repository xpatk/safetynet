package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.PersonInfoDTO;
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

@SpringBootTest
public class PersonInfoServiceTest {

    @MockitoBean
    private PersonService personService;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PersonInfoService personInfoService;

    private Person johnDoe;
    private Person janeDoe;
    private MedicalRecord johnRecord;
    private MedicalRecord janeRecord;

    @BeforeEach
    void setUp() {
        johnDoe = new Person("John", "Doe", "123 Main St", "City", "12345", "john@doe.com", "111-222-3333");
        janeDoe = new Person("Jane", "Doe", "456 Oak St", "City", "12345", "jane@doe.com", "444-555-6666");

        johnRecord = new MedicalRecord("John", "Doe", LocalDate.of(2010, 1, 1), List.of("med1"), List.of("allergy1"));
        janeRecord = new MedicalRecord("Jane", "Doe", LocalDate.of(1985, 5, 15), List.of("med2"), List.of("allergy2"));
    }

    @Test
    void testGetPersonInfoByLastName() {
        // GIVEN
        when(personService.getAllPersons()).thenReturn(List.of(johnDoe, janeDoe));
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(List.of(johnRecord, janeRecord));

        // WHEN
        List<PersonInfoDTO> result = personInfoService.getPersonInfoByLastName("Doe");

        // THEN
        assertThat(result).hasSize(2);

        PersonInfoDTO johnDTO = result.stream().filter(p -> p.getFirstName().equals("John")).findFirst().orElseThrow();
        PersonInfoDTO janeDTO = result.stream().filter(p -> p.getFirstName().equals("Jane")).findFirst().orElseThrow();

        assertThat(johnDTO.getAge()).isLessThanOrEqualTo(18);
        assertThat(johnDTO.getAddress()).isEqualTo("123 Main St");
        assertThat(johnDTO.getEmail()).isEqualTo("john@doe.com");
        assertThat(johnDTO.getMedications()).containsExactly("med1");
        assertThat(johnDTO.getAllergies()).containsExactly("allergy1");

        assertThat(janeDTO.getAge()).isGreaterThan(18);
        assertThat(janeDTO.getAddress()).isEqualTo("456 Oak St");
        assertThat(janeDTO.getEmail()).isEqualTo("jane@doe.com");
        assertThat(janeDTO.getMedications()).containsExactly("med2");
        assertThat(janeDTO.getAllergies()).containsExactly("allergy2");
    }
}
