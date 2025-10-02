package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object (DTO) representing households covered by fire stations
 * during a flood alert.
 *
 * Contains a map of addresses to lists of residents.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FloodStationsDTO {

    /** Map of addresses to residents living at that address */
    private Map<String, List<HouseholdInfo>> households;

    /**
     * Nested class representing information about a resident in a household.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HouseholdInfo {

        /** Resident's first name */
        private String firstName;

        /** Resident's last name */
        private String lastName;

        /** Resident's phone number */
        private String phone;

        /** Resident's age */
        private int age;

        /** List of medications the resident takes */
        private List<String> medications;

        /** List of allergies the resident has */
        private List<String> allergies;
    }
}
