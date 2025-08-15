package com.safetynet.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirestationCoverageDTO {
    private List<PersonInfoDTO> persons;
    private long adultCount;
    private long childCount;
}

