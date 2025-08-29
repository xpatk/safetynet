package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalrecord")
@AllArgsConstructor
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    /**
     * GET all medical records
     */
    @GetMapping
    @Operation(summary = "Get all medical records")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of all medical records accessed successfully.")
    })
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecordList = medicalRecordService.getAllMedicalRecords();
        log.info("Get all medical records ok");
        return ResponseEntity.ok(medicalRecordList);
    }

    /**
     * ADD a new medical record
     */
    @PostMapping
    @Operation(summary = "Add a new medical record")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Medical record created successfully.")
    })
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.addMedicalRecord(medicalRecord);
        log.info("Adding a medical record: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    /**
     * MODIFY an existing medical record
     */
    @PutMapping("/{firstName}/{lastName}")
    @Operation(summary = "Update a medical record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medical record updated successfully."),
            @ApiResponse(responseCode = "404", description = "Medical record not found.")
    })
    public ResponseEntity<String> updateMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecord updatedMedicalRecord) {

        log.info("Updating medical record for: {} {}", firstName, lastName);

        boolean recordIsUpdated = medicalRecordService.updateMedicalRecord(firstName, lastName, updatedMedicalRecord) != null;
        if (!recordIsUpdated) {
            log.error("Medical record not found for update: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The medical record not found.");
        }
        return ResponseEntity.ok("The medical record has been updated successfully.");
    }

    /**
     * DELETE an existing medical record
     */
    @DeleteMapping("/{firstName}/{lastName}")
    @Operation(summary = "Delete a medical record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medical record deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Medical record not found.")
    })
    public ResponseEntity<String> deleteMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) {

        log.info("Deleting medical record for: {} {}", firstName, lastName);
        boolean recordIsDeleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (!recordIsDeleted) {
            log.error("Medical record not found for deletion: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The medical record has not been deleted");
        }
        return ResponseEntity.ok("The medical record has been successfully deleted.");
    }
}
