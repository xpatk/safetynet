package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.FloodDTO;
import com.safetynet.safetynet.service.FloodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/flood")
@RequiredArgsConstructor
public class FloodController {

    private final FloodService floodService;

    /**
     * GET endpoint to retrieve households and residents served by a list of fire stations.
     *
     * @param stations list of fire station numbers
     * @return a {@link FloodDTO} containing households grouped by address
     */
    @GetMapping("/stations")
    @Operation(summary = "Get households by fire stations",
            description = "Returns a list of households (grouped by address) with resident details " +
                    "for all addresses covered by the given fire station numbers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flood data retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No data found for the given station numbers.")
    })
    public ResponseEntity<FloodDTO> getHouseholdsByStations(@RequestParam List<String> stations) {
        log.info("Fetching flood data for stations: {}", stations);
        FloodDTO floodDTO = floodService.getHouseholdsByStations(stations);

        if (floodDTO == null || floodDTO.getHouseholds().isEmpty()) {
            log.warn("No flood data found for stations: {}", stations);
            return ResponseEntity.notFound().build();
        }

        log.info("Flood data successfully retrieved for stations: {}", stations);
        return ResponseEntity.ok(floodDTO);
    }
}
