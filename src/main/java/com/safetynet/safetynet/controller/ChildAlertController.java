package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.ChildAlertDTO;
import com.safetynet.safetynet.service.ChildAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/childAlert")
@RequiredArgsConstructor
public class ChildAlertController {

    private final ChildAlertService childAlertService;

    /**
     * GET endpoint to retrieve a list of children and other household members
     * for a given address.
     *
     * @param address the address to search for children and household members
     * @return a {@link ChildAlertDTO} containing children and household members
     */
    @GetMapping
    @Operation(summary = "Get children and household members by address",
            description = "Returns a list of children (with age) and the other members of the household at the given address.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Child alert data retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No data found for the given address.")
    })
    public ResponseEntity<ChildAlertDTO> getChildAlert(@RequestParam String address) {
        log.info("Fetching child alert information for address: {}", address);
        ChildAlertDTO childAlert = childAlertService.getChildAlertByAddress(address);

        if (childAlert == null || (childAlert.getChildren().isEmpty() && childAlert.getOtherHouseholdMembers().isEmpty())) {
            log.warn("No child alert data found for address: {}", address);
            return ResponseEntity.notFound().build();
        }

        log.info("Child alert data successfully retrieved for address: {}", address);
        return ResponseEntity.ok(childAlert);
    }
}
