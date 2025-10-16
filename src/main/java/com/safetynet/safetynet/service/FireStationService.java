package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class FireStationService {

    private final FireStationRepository firestationRepository;

    public void addFireStation(FireStation firestation) {
        log.debug("Adding fire station at address: {}", firestation.getAddress());
        firestationRepository.addFireStation(firestation);
    }

    public FireStation updateFireStation(String address, FireStation updatedFireStation) {
        log.debug("Updating fire station at address: {}", address);
        return firestationRepository.updateFireStation(address, updatedFireStation);
    }

    public boolean deleteFireStation(String address) {
        log.debug("Deleting fire station at address: {}", address);
        return firestationRepository.deleteFireStation(address);
    }

    public List<FireStation> getAllFireStations() {
        log.debug("Fetching all fire stations");
        return firestationRepository.getAllFireStations();
    }
}
