package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.repository.impl.MedicalRecordRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link MedicalRecordRepositoryImpl}.
 * <p>
 * This class verifies CRUD operations for medical records
 * using a mocked {@link DataLoader}.
 */
class MedicalRecordRepositoryImplTest {

    private DataLoader dataLoader;
    private MedicalRecordRepositoryImpl repository;
    private List<MedicalRecord> mockData;

    /**
     * Initializes the test environment before each test.
     * <p>
     * Sets up the mocked DataLoader and sample medical records.
     */
    @BeforeEach
    void setUp() {
        dataLoader = mock(DataLoader.class);
        repository = new MedicalRecordRepositoryImpl(dataLoader);

        mockData = new ArrayList<>();
        mockData.add(new MedicalRecord("John", "Doe", LocalDate.of(1980, 1, 1),
                List.of("med1:100mg"), List.of("pollen")));
        mockData.add(new MedicalRecord("Jane", "Smith", LocalDate.of(1990, 2, 2),
                List.of("aspirin:50mg"), List.of("nuts")));

        when(dataLoader.getMedicalRecords()).thenReturn(mockData);
    }

    /**
     * Tests retrieval of all medical records.
     * <p>
     * Ensures that the repository correctly returns the list from the DataLoader.
     */
    @Test
    void testGetAllMedicalRecords() {
        List<MedicalRecord> result = repository.getAllMedicalRecords();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.getFirst().getFirstName());
        verify(dataLoader, times(1)).getMedicalRecords();
    }

    /**
     * Tests adding a new medical record.
     * <p>
     * Verifies that a new record is added and persisted through the DataLoader.
     */
    @Test
    void testAddMedicalRecord() {
        MedicalRecord newRecord = new MedicalRecord("Alice", "Brown", LocalDate.of(2000, 3, 3),
                List.of("ibuprofen:200mg"), List.of("gluten"));

        repository.addMedicalRecord(newRecord);

        assertTrue(mockData.contains(newRecord));
        verify(dataLoader, times(1)).setMedicalRecords(mockData);
    }

    /**
     * Tests successful update of an existing medical record.
     * <p>
     * Verifies that the record's data is updated and persisted.
     */
    @Test
    void testUpdateMedicalRecord() {
        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", LocalDate.of(1985, 5, 5),
                List.of("newmed:10mg"), List.of("dust"));

        MedicalRecord result = repository.updateMedicalRecord("John", "Doe", updatedRecord);

        assertNotNull(result);
        assertEquals(LocalDate.of(1985, 5, 5), result.getBirthdate());
        assertEquals(List.of("newmed:10mg"), result.getMedications());
        verify(dataLoader, times(1)).setMedicalRecords(mockData);
    }

    /**
     * Tests update operation when the target record does not exist.
     * <p>
     * Ensures that the method returns {@code null} and does not persist changes.
     */
    @Test
    void testUpdateMedicalRecordNotFound() {
        MedicalRecord updatedRecord = new MedicalRecord("Unknown", "Person", LocalDate.of(2000, 10, 10),
                List.of("none"), List.of("none"));

        MedicalRecord result = repository.updateMedicalRecord("Unknown", "Person", updatedRecord);

        assertNull(result);
        verify(dataLoader, never()).setMedicalRecords(anyList());
    }

    /**
     * Tests successful deletion of a medical record.
     * <p>
     * Verifies that the record is removed and persisted.
     */
    @Test
    void testDeleteMedicalRecord() {
        boolean result = repository.deleteMedicalRecord("Jane", "Smith");

        assertTrue(result);
        assertEquals(1, mockData.size());
        verify(dataLoader, times(1)).setMedicalRecords(mockData);
    }

    /**
     * Tests deletion of a non-existent medical record.
     * <p>
     * Verifies that the method returns {@code false} and does not persist changes.
     */
    @Test
    void testDeleteMedicalRecordNotFound() {
        boolean result = repository.deleteMedicalRecord("Unknown", "Person");

        assertFalse(result);
        verify(dataLoader, never()).setMedicalRecords(anyList());
    }

    /**
     * Tests finding a medical record by first and last name.
     * <p>
     * Verifies that the correct record is returned when it exists.
     */
    @Test
    void testFindByFirstNameAndLastName() {
        MedicalRecord result = repository.findByFirstNameAndLastName("John", "Doe");

        assertNotNull(result);
        assertEquals(LocalDate.of(1980, 1, 1), result.getBirthdate());
        verify(dataLoader, times(1)).getMedicalRecords();
    }

    /**
     * Tests searching for a record that does not exist.
     * <p>
     * Ensures that the method returns {@code null}.
     */
    @Test
    void testFindByFirstNameAndLastNameNotFound() {
        MedicalRecord result = repository.findByFirstNameAndLastName("Alice", "White");

        assertNull(result);
        verify(dataLoader, times(1)).getMedicalRecords();
    }
}
