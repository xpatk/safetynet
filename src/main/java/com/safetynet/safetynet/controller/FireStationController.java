package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.service.FireStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that manages FireStation mappings.
 * Provides endpoints to create, update, delete, and retrieve fire station mappings
 * between station numbers and addresses.
 */
@Slf4j
@RestController
@RequestMapping("/firestation")
@RequiredArgsConstructor
public class FireStationController {

    private final FireStationService fireStationService;

    @GetMapping
    @Operation(summary = "Get all fire stations")
    public ResponseEntity<List<FireStation>> getAllFireStations() {
        log.info("Fetching all fire station mappings");
        return ResponseEntity.ok(fireStationService.getAllFireStations());
    }

    @PostMapping
    @Operation(summary = "Add a new fire station mapping")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fire station mapping created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
        fireStationService.addFireStation(fireStation);
        log.info("Fire station mapping added: {}", fireStation);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fire station mapping created successfully.");
    }

    @PutMapping("/{address}")
    @Operation(summary = "Update station number for a fire station mapping")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fire station mapping updated successfully."),
            @ApiResponse(responseCode = "404", description = "Fire station mapping not found.")
    })
    public ResponseEntity<String> updateFireStation(
            @PathVariable String address,
            @RequestBody FireStation updatedStation) {

        boolean updated = fireStationService.updateFireStation(address, updatedStation) != null;
        if (!updated) {
            log.warn("Fire station mapping not found for address: {}", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fire station mapping not found.");
        }
        log.info("Fire station mapping updated for address: {}", address);
        return ResponseEntity.ok("Fire station mapping updated successfully.");
    }

    @DeleteMapping("/{address}")
    @Operation(summary = "Delete a fire station mapping")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fire station mapping deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Fire station mapping not found.")
    })
    public ResponseEntity<String> deleteFireStation(@PathVariable String address) {
        boolean deleted = fireStationService.deleteFireStation(address);
        if (!deleted) {
            log.warn("Fire station mapping not found for deletion: {}", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fire station mapping not found.");
        }
        log.info("Fire station mapping deleted for address: {}", address);
        return ResponseEntity.ok("Fire station mapping deleted successfully.");
    }
}
