package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO representing fire station coverage information.
 * Includes a list of covered persons and counts of adults and children.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirestationCoverageDTO {
    private List<PersonInfoDTO> persons;
    private long adultCount;
    private long childCount;

    /**
     * Nested DTO representing the details of a person covered by the fire station.
     */

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonInfoDTO {
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
    }
}


