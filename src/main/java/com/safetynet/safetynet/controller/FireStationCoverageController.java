package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.FireStationCoverageDTO;
import com.safetynet.safetynet.service.FireStationCoverageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * Controller responsible for handling fire station coverage requests.
 * Provides an endpoint to retrieve information about persons covered by a fire station,
 * including counts of adults and children.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class FireStationCoverageController {

    private final FireStationCoverageService coverageService;

    /**
     * Fetches coverage information for a given fire station number.
     * Returns a DTO containing the list of persons covered and counts of adults and children.
     */
    @GetMapping("/firestation")
    @Operation(summary = "Get firestation coverage by station number")
    public FireStationCoverageDTO getCoverage(@RequestParam String stationNumber) {
        log.info("Fetching coverage for station {}", stationNumber);
        return coverageService.getCoverageByStation(stationNumber);
    }
}

