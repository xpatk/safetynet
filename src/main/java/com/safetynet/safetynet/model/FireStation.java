package com.safetynet.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a fire station in the SafetyNet system.

 * This class is part of the domain model and is used internally
 * to map fire stations to their assigned addresses.

 * Fire station data is loaded from the data source (JSON file)
 * and is used by the application to determine:
 * - Which station covers a given household
 * - Fire station coverage reports
 * - Emergency response alerts
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireStation {

    /** Address covered by the fire station. Cannot be blank. */
    @NotBlank(message = "Address cannot be blank.")
    private String address;

    /** Station number assigned to the address. Cannot be blank. */
    @NotBlank(message = "Station number cannot be blank.")
    private String station;
}
