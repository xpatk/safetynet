package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.repository.FireStationRepository;
import com.safetynet.safetynet.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class FirestationService {

    private final FireStationRepository firestationRepository;

    public FireStation addFireStation(FireStation firestation) {
        return firestationRepository.addFireStation(firestation);
    }

    public FireStation updateFirestation(String address, FireStation updatedFirestation) {
        return firestationRepository.updateFirestation(address, updatedFirestation);
    }

    public boolean deleteFireStation(String address) {
        return firestationRepository.deleteFireStation(address);
    }

    public List<FireStation> getAllFireStations() {
        return firestationRepository.getAllFireStations();
    }
}
