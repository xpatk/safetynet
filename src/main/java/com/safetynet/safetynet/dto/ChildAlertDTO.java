package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlertDTO {

    private List<ChildInfo> children;
    private List<HouseholdMember> otherHouseholdMembers;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChildInfo {
        private String firstName;
        private String lastName;
        private int age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HouseholdMember {
        private String firstName;
        private String lastName;
    }
}

