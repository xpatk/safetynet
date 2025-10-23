package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.ChildAlertDTO;
import com.safetynet.safetynet.service.ChildAlertService;
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

@WebMvcTest(ChildAlertController.class)
class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChildAlertService childAlertService;

    private ChildAlertDTO mockChildAlertDTO;

    @BeforeEach
    void setUp() {
        mockChildAlertDTO = new ChildAlertDTO();

        mockChildAlertDTO.setChildren(List.of(
                new ChildAlertDTO.ChildInfo("John", "Doe", 10),
                new ChildAlertDTO.ChildInfo("Jane", "Doe", 8)
        ));

        mockChildAlertDTO.setHouseholdMembers(List.of(
                new ChildAlertDTO.HouseholdMember("Jack", "Doe"),
                new ChildAlertDTO.HouseholdMember("Jill", "Doe")
        ));
    }

    @Test
    @DisplayName("Test success - retrieve children for address")
    void testGetChildAlertSuccess() throws Exception {
        String address = "123 Test Street";
        when(childAlertService.getChildrenAtAddress(address)).thenReturn(mockChildAlertDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children").isArray())
                .andExpect(jsonPath("$.children[0].firstName").value("John"))
                .andExpect(jsonPath("$.children[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.children[0].age").value(10))
                .andExpect(jsonPath("$.children[1].firstName").value("Jane"))
                .andExpect(jsonPath("$.children[1].lastName").value("Doe"))
                .andExpect(jsonPath("$.children[1].age").value(8))
                .andExpect(jsonPath("$.householdMembers").isArray())
                .andExpect(jsonPath("$.householdMembers[0].firstName").value("Jack"))
                .andExpect(jsonPath("$.householdMembers[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.householdMembers[1].firstName").value("Jill"))
                .andExpect(jsonPath("$.householdMembers[1].lastName").value("Doe"));
    }

    @Test
    @DisplayName("Test 404 - no child alert data")
    void testGetChildAlertNotFound() throws Exception {
        String address = "123 Empty Street";
        when(childAlertService.getChildrenAtAddress(address))
                .thenReturn(new ChildAlertDTO(List.of(), List.of()));

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                        .param("address", address))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test 400 - missing address parameter")
    void testGetChildAlertMissingAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test service exception")
    void testGetChildAlertServiceException() throws Exception {
        String address = "123 Error Street";
        when(childAlertService.getChildrenAtAddress(address))
                .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                        .param("address", address))
                .andExpect(status().isInternalServerError());
    }
}
