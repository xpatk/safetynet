package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * Data Transfer Object (DTO) representing the result of a "fire station" request.

 * This DTO contains:
 * - The fire station number.
 * - A list of residents covered by the station, including their personal and medical information.

 * It is used by the application to provide responses for the
 * fire station endpoint in the SafetyNet system.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FireDTO {

    /** The number of the fire station. */
    private int stationNumber;
    /** List of residents under the coverage of this fire station. */
    private List<ResidentInfo> residents;

    /**
     * Nested class containing the personal and medical information
     * of a resident covered by the fire station.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
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
