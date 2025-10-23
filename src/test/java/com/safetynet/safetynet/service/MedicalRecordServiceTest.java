package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MedicalRecordServiceTest {

    @MockitoBean
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordService medicalRecordService;

    private MedicalRecord johnRecord;
    private MedicalRecord janeRecord;

    @BeforeEach
    void setUp() {
        johnRecord = new MedicalRecord("John", "Doe", LocalDate.of(2015, 1, 1), List.of("med1"), List.of("allergy1"));
        janeRecord = new MedicalRecord("Jane", "Doe", LocalDate.of(1990, 2, 2), List.of("med2"), List.of("allergy2"));
    }

    @Test
    void testGetAllMedicalRecords() {
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(List.of(johnRecord, janeRecord));

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecords();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(johnRecord, janeRecord);
        verify(medicalRecordRepository, times(1)).getAllMedicalRecords();
    }

    @Test
    void testAddMedicalRecord() {
        medicalRecordService.addMedicalRecord(johnRecord);
        verify(medicalRecordRepository, times(1)).addMedicalRecord(johnRecord);
    }

    @Test
    void testUpdateMedicalRecord() {
        when(medicalRecordRepository.updateMedicalRecord("John", "Doe", johnRecord)).thenReturn(johnRecord);

        MedicalRecord result = medicalRecordService.updateMedicalRecord("John", "Doe", johnRecord);

        assertThat(result).isEqualTo(johnRecord);
        verify(medicalRecordRepository, times(1)).updateMedicalRecord("John", "Doe", johnRecord);
    }

    @Test
    void testDeleteMedicalRecord() {
        when(medicalRecordRepository.deleteMedicalRecord("John", "Doe")).thenReturn(true);

        boolean deleted = medicalRecordService.deleteMedicalRecord("John", "Doe");

        assertThat(deleted).isTrue();
        verify(medicalRecordRepository, times(1)).deleteMedicalRecord("John", "Doe");
    }

    @Test
    void testFindByName() {
        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(johnRecord);

        MedicalRecord result = medicalRecordService.findByName("John", "Doe");

        assertThat(result).isEqualTo(johnRecord);
        verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }
}
