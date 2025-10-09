package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.PhoneAlertDTO;
import com.safetynet.safetynet.service.PhoneAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that handles the phone alert endpoint.
 * This endpoint allows retrieving the phone numbers of all residents
 * covered by a specific fire station. It is used to send emergency
 * text messages to households in the fire station's jurisdiction.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PhoneAlertController {

    private final PhoneAlertService phoneAlertService;

    /**
     * GET endpoint to retrieve phone numbers of residents by fire station number.
     *
     * @param fireStation the fire station number
     * @return a {@link PhoneAlertDTO} containing the list of phone numbers
     */
    @GetMapping("/phoneAlert")
    @Operation(
            summary = "Get phone numbers for a fire station",
            description = "Returns a list of phone numbers of all residents covered by the specified fire station."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Phone numbers retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No residents found for the given fire station.")
    })
    public PhoneAlertDTO getPhoneAlert(@RequestParam String fireStation) {
        log.info("Fetching phone alert for fireStation={}", fireStation);
        return phoneAlertService.getPhonesByFireStation(fireStation);
    }
}
