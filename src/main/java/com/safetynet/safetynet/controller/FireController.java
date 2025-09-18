package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.FireDTO;
import com.safetynet.safetynet.service.FireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/fire")
@RequiredArgsConstructor
public class FireController {

    private final FireService fireService;

    /**
     * GET endpoint to retrieve fire station number and residents' info for a given address.
     *
     * @param address the address to search
     * @return a {@link FireDTO} containing the station number and the list of residents
     */
    @GetMapping
    @Operation(summary = "Get firestation and residents by address",
            description = "Returns the fire station number serving the given address, along with resident details (age, phone, medications, allergies).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fire data retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No fire data found for the given address.")
    })
    public ResponseEntity<FireDTO> getFireInfo(@RequestParam String address) {
        log.info("Fetching fire info for address: {}", address);
        FireDTO fireDTO = fireService.getFireInfoByAddress(address);

        if (fireDTO == null || fireDTO.getResidents().isEmpty()) {
            log.warn("No fire data found for address: {}", address);
            return ResponseEntity.notFound().build();
        }

        log.info("Fire data successfully retrieved for address: {}", address);
        return ResponseEntity.ok(fireDTO);
    }
}
