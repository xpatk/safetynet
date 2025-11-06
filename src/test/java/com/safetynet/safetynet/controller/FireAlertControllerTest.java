package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.FireDTO;
import com.safetynet.safetynet.dto.FireDTO.ResidentInfo;
import com.safetynet.safetynet.service.FireAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link FireAlertController}.
 * <p>
 * Uses {@link WebMvcTest} to test the controller endpoints in isolation with
 * a mocked {@link FireAlertService}. Verifies behavior for successful requests,
 * missing parameters, empty responses, and service exceptions.
 * </p>
 */
@WebMvcTest(FireAlertController.class)
class FireAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireAlertService fireAlertService;

    private FireDTO mockFireDTO;

    /**
     * Sets up a mock {@link FireDTO} before each test.
     * Includes a fire station number and residents with medications and allergies.
     */
    @BeforeEach
    void setUp() {
        mockFireDTO = new FireDTO();
        mockFireDTO.setFireStationNumber(1);
        mockFireDTO.setResidents(List.of(
                new ResidentInfo("John", "Doe", "123-456-7890", 30, List.of("med1"), List.of("allergy1")),
                new ResidentInfo("Jane", "Doe", "987-654-3210", 25, List.of(), List.of())
        ));
    }

    /**
     * Tests successful retrieval of fire alert data for a given address.
     * Verifies that the returned JSON contains the correct fire station number and resident details.
     *
     * @throws Exception if the request execution fails
     */
    @Test
    @DisplayName("Test success - retrieve fire info for an address")
    void testGetFireInfoSuccess() throws Exception {
        String address = "123 Main St";
        when(fireAlertService.getFireAlertByAddress(address)).thenReturn(mockFireDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fireStationNumber").value(1))
                .andExpect(jsonPath("$.residents").isArray())
                .andExpect(jsonPath("$.residents[0].firstName").value("John"))
                .andExpect(jsonPath("$.residents[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.residents[0].phone").value("123-456-7890"))
                .andExpect(jsonPath("$.residents[0].age").value(30))
                .andExpect(jsonPath("$.residents[0].medications[0]").value("med1"))
                .andExpect(jsonPath("$.residents[0].allergies[0]").value("allergy1"))
                .andExpect(jsonPath("$.residents[1].firstName").value("Jane"))
                .andExpect(jsonPath("$.residents[1].lastName").value("Doe"))
                .andExpect(jsonPath("$.residents[1].phone").value("987-654-3210"))
                .andExpect(jsonPath("$.residents[1].age").value(25));
    }

    /**
     * Tests the scenario where no fire alert data exists for the given address.
     * Expects a 404 Not Found response.
     *
     * @throws Exception if the request execution fails
     */
    @Test
    @DisplayName("Test 404 - no fire info found")
    void testGetFireInfoNotFound() throws Exception {
        String address = "Unknown St";
        when(fireAlertService.getFireAlertByAddress(address))
                .thenReturn(new FireDTO(0, List.of()));

        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", address))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the scenario where the required "address" parameter is missing from the request.
     * Expects a 400 Bad Request response.
     *
     * @throws Exception if the request execution fails
     */
    @Test
    @DisplayName("Test 400 - missing address parameter")
    void testGetFireInfoMissingAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fire"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests the scenario where the service throws an exception during retrieval.
     * Expects a 500 Internal Server Error response.
     *
     * @throws Exception if the request execution fails
     */
    @Test
    @DisplayName("Test service exception")
    void testGetFireInfoServiceException() throws Exception {
        String address = "Error St";
        when(fireAlertService.getFireAlertByAddress(address))
                .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", address))
                .andExpect(status().isInternalServerError());
    }
}
