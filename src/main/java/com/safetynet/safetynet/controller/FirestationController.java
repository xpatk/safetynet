package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.FirestationCoverageDTO;
import com.safetynet.safetynet.service.FirestationCoverageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FirestationController {

    private final FirestationCoverageService coverageService;

    @GetMapping("/firestation")
    public FirestationCoverageDTO getCoverage(@RequestParam int stationNumber) {
        return coverageService.getCoverageByStation(stationNumber);
    }
}

