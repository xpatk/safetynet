package com.safetynet.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.dto.FloodStationsDTO;
import com.safetynet.safetynet.dto.FloodStationsDTO.HouseholdInfo;
import com.safetynet.safetynet.service.FloodAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FloodController.class)
class FloodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FloodAlertService floodService;

    @Autowired
    private ObjectMapper objectMapper;

    private FloodStationsDTO mockFloodData;

    @BeforeEach
    void setUp() {
        HouseholdInfo resident1 = new HouseholdInfo("John", "Doe", "123-456-7890", 35,
                List.of("med1"), List.of("Peanut"));
        HouseholdInfo resident2 = new HouseholdInfo("Jane", "Doe", "123-456-7891", 30,
                List.of("med2"), List.of("None"));

        mockFloodData = new FloodStationsDTO(Map.of(
                "123 Main St", List.of(resident1, resident2)
        ));
    }

    @Test
    @DisplayName("GET /flood/stations - Success")
    void testGetHouseholdsByStationsSuccess() throws Exception {
        List<String> stations = List.of("1", "2");
        when(floodService.getHouseholdsByStations(stations)).thenReturn(mockFloodData);

        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.households['123 Main St']").isArray())
                .andExpect(jsonPath("$.households['123 Main St'][0].firstName").value("John"))
                .andExpect(jsonPath("$.households['123 Main St'][0].lastName").value("Doe"))
                .andExpect(jsonPath("$.households['123 Main St'][0].phone").value("123-456-7890"))
                .andExpect(jsonPath("$.households['123 Main St'][0].age").value(35))
                .andExpect(jsonPath("$.households['123 Main St'][0].medications[0]").value("med1"))
                .andExpect(jsonPath("$.households['123 Main St'][0].allergies[0]").value("Peanut"))
                .andExpect(jsonPath("$.households['123 Main St'][1].firstName").value("Jane"));
    }

    @Test
    @DisplayName("GET /flood/stations - Not Found")
    void testGetHouseholdsByStationsNotFound() throws Exception {
        List<String> stations = List.of("3");
        when(floodService.getHouseholdsByStations(stations))
                .thenReturn(new FloodStationsDTO(Map.of())); // empty map

        mockMvc.perform(get("/flood/stations")
                        .param("stations", "3"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /flood/stations - Missing parameter")
    void testGetHouseholdsMissingParams() throws Exception {
        mockMvc.perform(get("/flood/stations"))
                .andExpect(status().isBadRequest());
    }
}
