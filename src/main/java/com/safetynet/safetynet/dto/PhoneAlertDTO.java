package com.safetynet.safetynet.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a list of phone numbers
 * for residents covered by a specific fire station.

 * This DTO is used in endpoints that return phone alerts for emergency notifications.
 */
@Getter
@AllArgsConstructor
public class PhoneAlertDTO {
    /** List of phone numbers to notify. */
    private List<String> phoneNumbers;
}