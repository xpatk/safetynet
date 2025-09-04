package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FloodDTO {

    private List<Household> households;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Household {
        private String address;
        private List<ResidentInfo> residents;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResidentInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;
    }
}