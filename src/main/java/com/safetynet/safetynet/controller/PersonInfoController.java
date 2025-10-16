package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.PersonInfoDTO;
import com.safetynet.safetynet.service.PersonInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for retrieving detailed information about persons.
 * This controller provides an endpoint to fetch personal details such as:
 * - First name, last name
 * - Age
 * - Address
 * - Email
 * - Medical records (medications, dosages, allergies)
 * The information is retrieved by using the person's last name as a query parameter.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/personInfo")
public class PersonInfoController {

    private final PersonInfoService personInfoService;

    /**
     * GET endpoint to retrieve detailed person information by last name.
     * Returns a list of objects containing personal details for all individuals matching the provided last name.
     *
     * @param lastName the last name of the person to search for
     * @return {@link ResponseEntity} containing the list of {@link PersonInfoDTO}
     *         and HTTP status:
     *             200 OK: list of persons found
     *             404 Not Found: no persons found with the given last name
     */
    @GetMapping
    @Operation(
            summary = "Get person info by last name",
            description = "Returns detailed personal information (age, address, medications, allergies, etc.) " +
                    "for individuals matching the specified last name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person info retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No person found with the given last name.")
    })
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfo(@RequestParam String lastName) {
        log.info("Fetching person info for lastName={}", lastName);

        List<PersonInfoDTO> infoList = personInfoService.getPersonInfoByLastName(lastName);

        if (infoList.isEmpty()) {
            log.warn("No person found with lastName={}", lastName);
            return ResponseEntity.notFound().build();
        }

        log.info("Retrieved {} person(s) for lastName={}", infoList.size(), lastName);
        return ResponseEntity.ok(infoList);
    }
}