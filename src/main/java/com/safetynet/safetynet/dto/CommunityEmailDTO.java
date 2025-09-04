package com.safetynet.safetynet.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityEmailDTO {
    private String city;
    private List<String> emails;
}

