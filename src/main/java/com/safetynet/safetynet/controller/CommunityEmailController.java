package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.CommunityEmailDTO;
import com.safetynet.safetynet.service.CommunityEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/communityEmail")
@RequiredArgsConstructor
public class CommunityEmailController {

    private final CommunityEmailService communityEmailService;

    /**
     * GET endpoint to retrieve all email addresses of inhabitants for a given city.
     *
     * @param city the name of the city
     * @return a {@link CommunityEmailDTO} containing the city and its email list
     */
    @GetMapping
    @Operation(summary = "Get all email addresses by city",
            description = "Returns a list of email addresses for all residents living in the specified city.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Emails retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No emails found for the given city.")
    })
    public ResponseEntity<CommunityEmailDTO> getCommunityEmails(@RequestParam String city) {
        log.info("Fetching community emails for city: {}", city);
        CommunityEmailDTO communityEmailDTO = communityEmailService.getEmailsByCity(city);

        if (communityEmailDTO == null || communityEmailDTO.getEmails().isEmpty()) {
            log.warn("No emails found for city: {}", city);
            return ResponseEntity.notFound().build();
        }

        log.info("Emails successfully retrieved for city: {}", city);
        return ResponseEntity.ok(communityEmailDTO);
    }
}
