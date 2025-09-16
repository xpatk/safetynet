package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing the result of a "community email" request.

 * This DTO contains:
 * - The name of the city.
 * - A list of email addresses of all inhabitants living in that city.

 * It is used by the application to provide responses for the
 * community email endpoint in the SafetyNet system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityEmailDTO {

    /** The city for which email addresses are collected. */
    private String city;
    /** List of email addresses of all inhabitants living in the given city. */
    private List<String> emails;

}
