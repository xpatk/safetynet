package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing the result of a "child alert" request.

 * This DTO contains information about:
 * - Children living at a given address (with their age).
 * - Other household members at the same address.

 * It is used by the application to provide responses for the
 * child alert endpoint in the SafetyNet system.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlertDTO {

    /** List of children living at the given address. */
    private List<ChildInfo> children;
    /** List of other household members living at the same address. */
    private List<HouseholdMember> householdMembers;

    /**
     * Represents information about a child living at the address,
     * including first name, last name, and age.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChildInfo {
        private String firstName;
        private String lastName;
        private int age;
    }

    /**
     * Represents information about another household member
     * living at the same address as the children.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HouseholdMember {
        private String firstName;
        private String lastName;
    }
}

