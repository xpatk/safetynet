package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing the detailed information
 * of a person, including personal, contact, and medical details.
 *
 * This DTO is used in endpoints that return individual or grouped
 * information about persons in the SafetyNet system.
 */

@AllArgsConstructor
@NoArgsConstructor

public class PersonInfoDTO {
    /** Person's first name. */
    private String firstName;
    /** Person's last name. */
    private String lastName;
    /** Person's address. */
    private String address;
    /** Person's age. */
    private int age;
    /** Person's email address. */
    private String email;
    /** List of medications the person takes. */
    private List<String> medications;
    /** List of allergies the person has. */
    private List<String> allergies;
}