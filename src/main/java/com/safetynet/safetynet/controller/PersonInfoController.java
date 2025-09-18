package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.PersonInfoDTO;
import com.safetynet.safetynet.service.PersonInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that handles the person info endpoint.
 * This endpoint allows retrieving detailed information about a person,
 * including their age, address, email, medications, and allergies.
 * It is based on the person's last name.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonInfoController {

    private final PersonInfoService personInfoService;

    /**
     * GET endpoint to retrieve detailed person information by last name.
     *
     * @param lastName the last name of the person
     * @return a list of {@link PersonInfoDTO} objects containing person details
     */
    @GetMapping("/personInfo")
    @Operation(
            summary = "Get person info by last name",
            description = "Returns detailed personal information (age, address, medications, allergies, etc.) "
                    + "for individuals matching the specified last name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person info retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No person found with the given last name.")
    })
    public List<PersonInfoDTO> getPersonInfo(@RequestParam String lastName) {
        log.info("Fetching info for lastName={}", lastName);
        return personInfoService.getPersonInfoByLastName(lastName);
    }
}
