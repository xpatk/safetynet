package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.impl.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PersonRepositoryImpl}.
 * <p>
 * This class verifies CRUD operations for persons
 * using a mocked {@link DataLoader}.
 */
class PersonRepositoryImplTest {

    private DataLoader dataLoader;
    private PersonRepositoryImpl repository;
    private List<Person> mockData;

    /**
     * Initializes the test environment before each test.
     * <p>
     * Sets up a mock DataLoader and sample person data.
     */
    @BeforeEach
    void setUp() {
        dataLoader = mock(DataLoader.class);
        repository = new PersonRepositoryImpl(dataLoader);

        mockData = new ArrayList<>();
        mockData.add(new Person("John", "Doe", "123 Main St", "Culver", "97451", "841-874-6512", "john.doe@example.com"));
        mockData.add(new Person("Jane", "Smith", "456 Oak St", "Springfield", "12345", "123-456-7890", "jane.smith@example.com"));

        when(dataLoader.getPersons()).thenReturn(mockData);
    }

    /**
     * Tests retrieval of all persons.
     * <p>
     * Ensures that the repository correctly returns the list of persons from the DataLoader.
     */
    @Test
    void testGetAllPersons() {
        List<Person> result = repository.getAllPersons();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(dataLoader, times(1)).getPersons();
    }

    /**
     * Tests adding a new person.
     * <p>
     * Verifies that the person is added and persisted through the DataLoader.
     */
    @Test
    void testAddPerson() {
        Person newPerson = new Person("Alice", "Brown", "789 Pine St", "Boston", "02108", "555-555-5555", "alice.brown@example.com");

        repository.addPerson(newPerson);

        assertTrue(mockData.contains(newPerson));
        verify(dataLoader, times(1)).setPersons(mockData);
    }

    /**
     * Tests successful update of an existing person.
     * <p>
     * Ensures that the person's data is updated and persisted correctly.
     */
    @Test
    void testUpdatePerson() {
        Person updatedPerson = new Person("John", "Doe", "999 Elm St", "Culver", "97451", "000-000-0000", "john.new@example.com");

        Person result = repository.updatePerson("John", "Doe", updatedPerson);

        assertNotNull(result);
        assertEquals("999 Elm St", result.getAddress());
        assertEquals("john.new@example.com", result.getEmail());
        verify(dataLoader, times(1)).setPersons(mockData);
    }

    /**
     * Tests update operation when the target person does not exist.
     * <p>
     * Verifies that the method returns {@code null} and does not persist any changes.
     */
    @Test
    void testUpdatePersonNotFound() {
        Person updatedPerson = new Person("Unknown", "Person", "No Address", "Nowhere", "00000", "000-000-0000", "none@example.com");

        Person result = repository.updatePerson("Unknown", "Person", updatedPerson);

        assertNull(result);
        verify(dataLoader, never()).setPersons(anyList());
    }

    /**
     * Tests successful deletion of a person.
     * <p>
     * Verifies that the person is removed from the list and persisted through the DataLoader.
     */
    @Test
    void testDeletePerson() {
        boolean result = repository.deletePerson("Jane", "Smith");

        assertTrue(result);
        assertEquals(1, mockData.size());
        verify(dataLoader, times(1)).setPersons(mockData);
    }

    /**
     * Tests deletion of a non-existent person.
     * <p>
     * Verifies that the method returns {@code false} and no changes are persisted.
     */
    @Test
    void testDeletePersonNotFound() {
        boolean result = repository.deletePerson("Unknown", "Person");

        assertFalse(result);
        verify(dataLoader, never()).setPersons(anyList());
    }
}
