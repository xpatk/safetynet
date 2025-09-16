package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO used to transfer all email addresses of inhabitants for a given city.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityEmailDTO {

    private String city;
    private List<String> emails;

    @Override
    public String toString() {
        return "CommunityEmailDTO{" +
                "city='" + city + '\'' +
                ", emails=" + emails +
                '}';
    }
}
