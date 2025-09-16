package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing fire station coverage information.

 * This DTO contains:
 * - A list of persons covered by the fire station.
 * - The total count of adults.
 * - The total count of children.

 * It is used by the application to provide responses for endpoints
 * returning fire station coverage details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationCoverageDTO {
    /** List of persons covered by the fire station. */
    private List<PersonInfoDTO> persons;
    /** Number of adults covered by the fire station. */
    private long adultCount;
    /** Number of children (18 or under) covered by the fire station. */
    private long childCount;

    /**
     * Nested DTO representing the details of a person covered by the fire station.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonInfoDTO {
        /** Person's first name. */
        private String firstName;
        /** Person's last name. */
        private String lastName;
        /** Person's address. */
        private String address;
        /** Person's phone number. */
        private String phone;
    }
}


