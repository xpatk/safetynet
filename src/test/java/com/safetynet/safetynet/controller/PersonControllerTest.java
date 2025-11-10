package com.safetynet.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link PersonController}.
 *
 * <p>
 * Uses {@link WebMvcTest} to test person endpoints in isolation
 * with a mocked {@link PersonService}. Verifies CRUD operations for persons.
 * </p>
 */
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    private Person mockPerson;
    private Person updatedPerson;

    /**
     * Initializes mock person data before each test.
     */
    @BeforeEach
    void setUp() {
        mockPerson = new Person();
        mockPerson.setFirstName("John");
        mockPerson.setLastName("Doe");
        mockPerson.setAddress("123 Main St");
        mockPerson.setCity("Culver");
        mockPerson.setZip("97451");
        mockPerson.setPhone("123-456-7890");
        mockPerson.setEmail("john.doe@example.com");

        updatedPerson = new Person();
        updatedPerson.setFirstName("John");
        updatedPerson.setLastName("Doe");
        updatedPerson.setAddress("456 Oak Ave");
        updatedPerson.setCity("Culver");
        updatedPerson.setZip("97451");
        updatedPerson.setPhone("123-000-9999");
        updatedPerson.setEmail("john.new@example.com");
    }

    /**
     * Tests retrieval of all persons successfully.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("GET all persons - Success")
    void testGetAllPersonsSuccess() throws Exception {
        when(personService.getAllPersons()).thenReturn(List.of(mockPerson));

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].city").value("Culver"))
                .andExpect(jsonPath("$[0].zip").value("97451"))
                .andExpect(jsonPath("$[0].phone").value("123-456-7890"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    /**
     * Tests creation of a new person successfully.
     *
     * <p>
     * Corrected to use thenReturn() instead of doNothing() because addPerson
     * returns a Person object instead of being void.
     * </p>
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("POST person - Success")
    void testAddPersonSuccess() throws Exception {
        when(personService.addPerson(any(Person.class))).thenReturn(mockPerson);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPerson)))
                .andExpect(status().isOk())
                .andExpect(content().string("Person created successfully."));

        verify(personService, times(1)).addPerson(any(Person.class));
    }

    /**
     * Tests updating an existing person successfully.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("PUT person - Success")
    void testUpdatePersonSuccess() throws Exception {
        when(personService.updatePerson(eq("John"), eq("Doe"), any(Person.class)))
                .thenReturn(updatedPerson);

        mockMvc.perform(put("/persons/John/Doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isOk())
                .andExpect(content().string("Person updated successfully."));
    }

    /**
     * Tests updating a non-existing person (not found scenario).
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("PUT person - Not Found")
    void testUpdatePersonNotFound() throws Exception {
        when(personService.updatePerson(eq("Jane"), eq("Smith"), any(Person.class)))
                .thenReturn(null);

        mockMvc.perform(put("/persons/Jane/Smith")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPerson)))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests deletion of a person successfully.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("DELETE person - Success")
    void testDeletePersonSuccess() throws Exception {
        when(personService.deletePerson("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/persons/John/Doe"))
                .andExpect(status().isOk())
                .andExpect(content().string("Person deleted successfully."));
    }

    /**
     * Tests deletion of a non-existing person (not found scenario).
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("DELETE person - Not Found")
    void testDeletePersonNotFound() throws Exception {
        when(personService.deletePerson("Jane", "Smith")).thenReturn(false);

        mockMvc.perform(delete("/persons/Jane/Smith"))
                .andExpect(status().isNotFound());
    }
}
