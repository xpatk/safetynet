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
public class FireDTO {

    private int stationNumber; // The number of the firestation
    private List<ResidentInfo> residents; // The list of residents under this address

    /**
     * Class containing the information of the concerned residents
     */
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
