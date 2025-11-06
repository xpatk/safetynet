package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.FireStationCoverageDTO;
import com.safetynet.safetynet.dto.FireStationCoverageDTO.PersonInfoDTO;
import com.safetynet.safetynet.service.FireStationCoverageService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Test class for {@link FireStationCoverageController}.
 * <p>
 * Uses {@link WebMvcTest} to test the fire station coverage endpoints in isolation with a mocked {@link FireStationCoverageService}.
 * Verifies behavior for retrieving fire station coverage data by station number, including adult/child counts and person details.
 * </p>
 */
@WebMvcTest(FireStationCoverageController.class)
class FireStationCoverageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireStationCoverageService coverageService;

    @Autowired
    private ObjectMapper objectMapper;

    private FireStationCoverageDTO mockCoverageDTO;

    /**
     * Initializes a sample {@link FireStationCoverageDTO} object before each test.
     */
    @BeforeEach
    void setUp() {
        mockCoverageDTO = new FireStationCoverageDTO();
        mockCoverageDTO.setAdultCount(2);
        mockCoverageDTO.setChildCount(1);
        mockCoverageDTO.setPersons(List.of(
                new PersonInfoDTO("John", "Doe", "123 Main St", "123-456-7890"),
                new PersonInfoDTO("Jane", "Doe", "123 Main St", "123-456-7891")
        ));
    }

    /**
     * Tests retrieval of fire station coverage by station number successfully.
     *
     * @throws Exception if the request execution fails
     */
    @Test
    @DisplayName("GET firestation coverage by station number - Success")
    void testGetCoverageSuccess() throws Exception {
        String stationNumber = "1";
        when(coverageService.getCoverageByStation(stationNumber)).thenReturn(mockCoverageDTO);

        mockMvc.perform(get("/firestation")
                        .param("stationNumber", stationNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount").value(2))
                .andExpect(jsonPath("$.childCount").value(1))
                .andExpect(jsonPath("$.persons").isArray())
                .andExpect(jsonPath("$.persons[0].firstName").value("John"))
                .andExpect(jsonPath("$.persons[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.persons[0].address").value("123 Main St"))
                .andExpect(jsonPath("$.persons[0].phone").value("123-456-7890"))
                .andExpect(jsonPath("$.persons[1].firstName").value("Jane"))
                .andExpect(jsonPath("$.persons[1].lastName").value("Doe"))
                .andExpect(jsonPath("$.persons[1].address").value("123 Main St"))
                .andExpect(jsonPath("$.persons[1].phone").value("123-456-7891"));
    }

    /**
     * Tests the behavior when the station number parameter is missing.
     *
     * @throws Exception if the request execution fails
     */
    @Test
    @DisplayName("GET firestation coverage missing station number - Bad Request")
    void testGetCoverageMissingParam() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isBadRequest());
    }
}
