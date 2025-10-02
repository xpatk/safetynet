package com.safetynet.safetynet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a medical record associated with a person.

 * This class is part of the domain model and is used internally
 * to store health-related information for individuals, including:
 * - Personal identifiers (first name, last name, birthdate)
 * - List of prescribed medications with dosages
 * - List of known allergies

 * Medical records are loaded from the data source (JSON file) and
 * used across the application to provide emergency-related information
 * through various endpoints such as child alerts and fire reports.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MedicalRecord {

    /** First name of the individual. Cannot be empty. */
    @NotBlank(message = "First name cannot be empty.")
    private String firstName;
    /** Last name of the individual. Cannot be empty. */
    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;
    /** Birthdate of the individual. Cannot be empty. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @NotNull(message = "Birthdate cannot be empty.")
    private LocalDate birthdate;
    /** List of medications prescribed to the individual. Includes name and dosage if applicable. */
    private List<String> medications;
    /** List of known allergies of the individual. */
    private List<String> allergies;
}
