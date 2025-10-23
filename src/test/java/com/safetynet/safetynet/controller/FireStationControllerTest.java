package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.service.FireStationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireStationService fireStationService;

    @Autowired
    private ObjectMapper objectMapper;

    private FireStation station;

    @BeforeEach
    void setUp() {
        station = new FireStation();
        station.setAddress("123 Main St");
        station.setStation("1");
    }

    @Test
    @DisplayName("GET all fire stations")
    void testGetAllFireStations() throws Exception {
        when(fireStationService.getAllFireStations()).thenReturn(List.of(station));

        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].station").value("1"));
    }

    @Test
    @DisplayName("POST add a fire station")
    void testAddFireStation() throws Exception {
        doNothing().when(fireStationService).addFireStation(station);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(station)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Fire station mapping created successfully."));

        verify(fireStationService, times(1)).addFireStation(station);
    }

    @Test
    @DisplayName("PUT update fire station successfully")
    void testUpdateFireStationSuccess() throws Exception {
        when(fireStationService.updateFireStation(eq("123 Main St"), any(FireStation.class)))
                .thenReturn(station);

        mockMvc.perform(put("/firestation/123 Main St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(station)))
                .andExpect(status().isOk())
                .andExpect(content().string("Fire station mapping updated successfully."));
    }

    @Test
    @DisplayName("PUT update fire station not found")
    void testUpdateFireStationNotFound() throws Exception {
        when(fireStationService.updateFireStation(eq("Unknown St"), any(FireStation.class)))
                .thenReturn(null);

        mockMvc.perform(put("/firestation/Unknown St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(station)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Fire station mapping not found."));
    }

    @Test
    @DisplayName("DELETE fire station successfully")
    void testDeleteFireStationSuccess() throws Exception {
        when(fireStationService.deleteFireStation("123 Main St")).thenReturn(true);

        mockMvc.perform(delete("/firestation/123 Main St"))
                .andExpect(status().isOk())
                .andExpect(content().string("Fire station mapping deleted successfully."));
    }

    @Test
    @DisplayName("DELETE fire station not found")
    void testDeleteFireStationNotFound() throws Exception {
        when(fireStationService.deleteFireStation("Unknown St")).thenReturn(false);

        mockMvc.perform(delete("/firestation/Unknown St"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Fire station mapping not found."));
    }
}
