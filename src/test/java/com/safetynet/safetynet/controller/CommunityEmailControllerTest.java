package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.CommunityEmailDTO;
import com.safetynet.safetynet.service.CommunityEmailService;
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

@WebMvcTest(CommunityEmailController.class)
class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommunityEmailService communityEmailService;

    private CommunityEmailDTO mockCommunityEmailDTO;

    @BeforeEach
    void setUp() {
        mockCommunityEmailDTO = new CommunityEmailDTO();
        mockCommunityEmailDTO.setCity("TestCity");
        mockCommunityEmailDTO.setEmails(List.of("john@doe.com", "jane@doe.com"));
    }

    @Test
    @DisplayName("Test success - retrieve emails for a city")
    void testGetCommunityEmailsSuccess() throws Exception {
        String city = "TestCity";
        when(communityEmailService.getEmailsByCity(city)).thenReturn(mockCommunityEmailDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("TestCity"))
                .andExpect(jsonPath("$.emails").isArray())
                .andExpect(jsonPath("$.emails[0]").value("john@doe.com"))
                .andExpect(jsonPath("$.emails[1]").value("jane@doe.com"));
    }

    @Test
    @DisplayName("Test 404 - no emails found")
    void testGetCommunityEmailsNotFound() throws Exception {
        String city = "EmptyCity";
        when(communityEmailService.getEmailsByCity(city))
                .thenReturn(new CommunityEmailDTO(city, List.of()));

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test 400 - missing city parameter")
    void testGetCommunityEmailsMissingCity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test service exception")
    void testGetCommunityEmailsServiceException() throws Exception {
        String city = "ErrorCity";
        when(communityEmailService.getEmailsByCity(city))
                .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isInternalServerError());
    }
}
