package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.repository.impl.FireStationRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link FireStationRepositoryImpl}.
 * <p>
 * This class verifies the correct behavior of CRUD operations
 * for fire stations using a mocked {@link DataLoader}.
 */
class FireStationRepositoryImplTest {

    private DataLoader dataLoader;
    private FireStationRepositoryImpl repository;

    private List<FireStation> mockData;

    /**
     * Sets up the test environment before each test.
     * <p>
     * Initializes the mocked DataLoader and sample fire station data.
     */
    @BeforeEach
    void setUp() {
        dataLoader = mock(DataLoader.class);
        repository = new FireStationRepositoryImpl(dataLoader);

        mockData = new ArrayList<>();
        mockData.add(new FireStation("123 Main St", "1"));
        mockData.add(new FireStation("456 Oak Ave", "2"));

        when(dataLoader.getFireStations()).thenReturn(mockData);
    }

    /**
     * Tests retrieval of all fire stations.
     * <p>
     * Verifies that the repository correctly returns the list
     * of fire stations provided by the DataLoader.
     */
    @Test
    void testGetAllFireStations() {
        List<FireStation> result = repository.getAllFireStations();

        assertEquals(2, result.size());
        assertEquals("123 Main St", result.get(0).getAddress());
        verify(dataLoader, times(1)).getFireStations();
    }

    /**
     * Tests adding a new fire station.
     * <p>
     * Ensures that the new fire station is added to the list
     * and persisted using the DataLoader.
     */
    @Test
    void testAddFireStation() {
        FireStation newStation = new FireStation("789 Pine Rd", "3");

        repository.addFireStation(newStation);

        assertTrue(mockData.contains(newStation));
        verify(dataLoader, times(1)).setFireStations(mockData);
    }

    /**
     * Tests updating an existing fire station.
     * <p>
     * Verifies that the station number is updated for an existing address
     * and that the updated list is persisted.
     */
    @Test
    void testUpdateFireStation() {
        FireStation updatedStation = new FireStation("123 Main St", "5");

        FireStation result = repository.updateFireStation("123 Main St", updatedStation);

        assertNotNull(result);
        assertEquals("5", result.getStation());
        verify(dataLoader, times(1)).setFireStations(mockData);
    }

    /**
     * Tests updating a non-existent fire station.
     * <p>
     * Verifies that the method returns {@code null} and does not attempt to persist changes.
     */
    @Test
    void testUpdateFireStationNotFound() {
        FireStation updatedStation = new FireStation("999 Elm St", "9");

        FireStation result = repository.updateFireStation("999 Elm St", updatedStation);

        assertNull(result);
        verify(dataLoader, never()).setFireStations(anyList());
    }

    /**
     * Tests successful deletion of a fire station.
     * <p>
     * Verifies that the station is removed and changes are persisted.
     */
    @Test
    void testDeleteFireStation() {
        boolean result = repository.deleteFireStation("456 Oak Ave");

        assertTrue(result);
        verify(dataLoader, times(1)).setFireStations(mockData);
        assertEquals(1, mockData.size());
    }

    /**
     * Tests deletion of a non-existent fire station.
     * <p>
     * Verifies that the method returns {@code false} and does not persist changes.
     */
    @Test
    void testDeleteFireStationNotFound() {
        boolean result = repository.deleteFireStation("999 Elm St");

        assertFalse(result);
        verify(dataLoader, never()).setFireStations(anyList());
    }
}
