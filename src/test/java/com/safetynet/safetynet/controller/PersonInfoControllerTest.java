package com.safetynet.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.dto.PersonInfoDTO;
import com.safetynet.safetynet.service.PersonInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link PersonInfoController}.
 * <p>
 * Uses {@link WebMvcTest} to test person info endpoints in isolation
 * with a mocked {@link PersonInfoService}. Verifies retrieval of person information by last name.
 * </p>
 */
@WebMvcTest(PersonInfoController.class)
class PersonInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonInfoService personInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    private PersonInfoDTO mockPerson;

    /**
     * Initializes mock person info data before each test.
     */
    @BeforeEach
    void setUp() {
        mockPerson = new PersonInfoDTO();
        mockPerson.setFirstName("John");
        mockPerson.setLastName("Doe");
        mockPerson.setAddress("123 Main St");
        mockPerson.setEmail("john.doe@example.com");
        mockPerson.setAge(35);
        mockPerson.setMedications(List.of("aspirin:100mg"));
        mockPerson.setAllergies(List.of("peanut"));
    }

    /**
     * Tests retrieval of person info successfully when persons are found.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("GET /personInfo - Success when persons found")
    void testGetPersonInfoSuccess() throws Exception {
        String lastName = "Doe";
        when(personInfoService.getPersonInfoByLastName(lastName))
                .thenReturn(List.of(mockPerson));

        mockMvc.perform(get("/personInfo")
                        .param("lastName", lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].age").value(35))
                .andExpect(jsonPath("$[0].medications[0]").value("aspirin:100mg"))
                .andExpect(jsonPath("$[0].allergies[0]").value("peanut"));
    }

    /**
     * Tests retrieval of person info when no persons are found (404 Not Found).
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("GET /personInfo - Not Found when list empty")
    void testGetPersonInfoNotFound() throws Exception {
        String lastName = "Unknown";
        when(personInfoService.getPersonInfoByLastName(lastName))
                .thenReturn(List.of());

        mockMvc.perform(get("/personInfo")
                        .param("lastName", lastName))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests retrieval of person info when the lastName parameter is missing (400 Bad Request).
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("GET /personInfo - Missing lastName parameter")
    void testGetPersonInfoMissingParam() throws Exception {
        mockMvc.perform(get("/personInfo"))
                .andExpect(status().isBadRequest());
    }
}
