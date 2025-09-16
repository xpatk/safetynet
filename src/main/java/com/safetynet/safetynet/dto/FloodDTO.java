package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing flood alert information.

 * This DTO contains a list of households affected by flooding,
 * with details about each resident including personal and medical information.
 */
@Getter
@AllArgsConstructor
public class FloodDTO {

    /** List of households affected by flooding. */
    private List<Household> households;

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Household {
        /** Address of the household. */
        private String address;
        /** List of residents living in the household. */
        private List<ResidentInfo> residents;
    }

    /**
     * Nested DTO representing the personal and medical information of a resident.
     */
    @Getter
    @AllArgsConstructor
    public static class ResidentInfo {
        /** Resident's first name. */
        private String firstName;
        /** Resident's last name. */
        private String lastName;
        /** Resident's phone number. */
        private String phone;
        /** Resident's age. */
        private int age;
        /** List of medications the resident takes. */
        private List<String> medications;
        /** List of allergies the resident has. */
        private List<String> allergies;
    }
}