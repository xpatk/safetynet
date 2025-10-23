package com.safetynet.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.dto.PhoneAlertDTO;
import com.safetynet.safetynet.service.PhoneAlertService;
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

@WebMvcTest(PhoneAlertController.class)
class PhoneAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhoneAlertService phoneAlertService;

    @Autowired
    private ObjectMapper objectMapper;

    private PhoneAlertDTO mockPhoneAlertDTO;

    @BeforeEach
    void setUp() {
        mockPhoneAlertDTO = new PhoneAlertDTO(List.of("123-456-7890", "098-765-4321"));
    }

    @Test
    @DisplayName("GET /phoneAlert - Success")
    void testGetPhoneAlertSuccess() throws Exception {
        String stationNumber = "1";
        when(phoneAlertService.getPhonesByFireStation(stationNumber)).thenReturn(mockPhoneAlertDTO);

        mockMvc.perform(get("/phoneAlert")
                        .param("fireStation", stationNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumbers").isArray())
                .andExpect(jsonPath("$.phoneNumbers[0]").value("123-456-7890"))
                .andExpect(jsonPath("$.phoneNumbers[1]").value("098-765-4321"));
    }

    @Test
    @DisplayName("GET /phoneAlert - Empty list (no residents found)")
    void testGetPhoneAlertEmptyPhones() throws Exception {
        String stationNumber = "99";
        when(phoneAlertService.getPhonesByFireStation(stationNumber)).thenReturn(new PhoneAlertDTO(List.of()));

        mockMvc.perform(get("/phoneAlert")
                        .param("fireStation", stationNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumbers").isEmpty());
    }
}
