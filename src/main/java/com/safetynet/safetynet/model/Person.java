package com.safetynet.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents a person in the SafetyNet system.
 *
 * This class is part of the domain model and is used internally
 * to store personal details about individuals, including:
 * - Identity information (first name, last name)
 * - Contact information (address, city, zip, phone, email)
 *
 * Person records are loaded from the data source (JSON file) and
 * are used across the application for generating alerts and reports
 * such as fire station coverage, child alerts, and emergency contacts.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    /** First name of the person. Cannot be empty. */
    @NotBlank(message = "First name cannot be empty.")
    private String firstName;

    /** Last name of the person. Cannot be empty. */
    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;

    /** Street address of the person. */
    private String address;

    /** City where the person resides. */
    private String city;

    /** Postal code of the person's address. */
    private String zip;

    /** Phone number of the person. */
    private String phone;

    /** Email address of the person. */
    private String email;
}