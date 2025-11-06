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

/**
 * Test class for {@link CommunityEmailController}.
 * <p>
 * Uses {@link WebMvcTest} to test the controller endpoints in isolation with
 * a mocked {@link CommunityEmailService}. Verifies behavior for successful requests,
 * missing parameters, empty responses, and exceptions.
 * </p>
 */
@WebMvcTest(CommunityEmailController.class)
class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommunityEmailService communityEmailService;

    private CommunityEmailDTO mockCommunityEmailDTO;

    /**
     * Sets up a mock {@link CommunityEmailDTO} before each test.
     * Includes a city and a list of email addresses for testing.
     */
    @BeforeEach
    void setUp() {
        mockCommunityEmailDTO = new CommunityEmailDTO();
        mockCommunityEmailDTO.setCity("TestCity");
        mockCommunityEmailDTO.setEmails(List.of("john@doe.com", "jane@doe.com"));
    }

    /**
     * Tests the successful retrieval of community emails for a given city.
     * Verifies the returned JSON contains the correct city and email addresses.
     *
     * @throws Exception if the request execution fails
     */
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

    /**
     * Tests the scenario where no emails exist for the given city.
     * Expects a 404 Not Found response.
     *
     * @throws Exception if the request execution fails
     */
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

    /**
     * Tests the scenario where the required "city" parameter is missing from the request.
     * Expects a 400 Bad Request response.
     *
     * @throws Exception if the request execution fails
     */
    @Test
    @DisplayName("Test 400 - missing city parameter")
    void testGetCommunityEmailsMissingCity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail"))
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
    void testGetCommunityEmailsServiceException() throws Exception {
        String city = "ErrorCity";
        when(communityEmailService.getEmailsByCity(city))
                .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isInternalServerError());
    }
}
