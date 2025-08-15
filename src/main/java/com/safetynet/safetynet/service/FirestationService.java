package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.repository.FirestationRepository;
import com.safetynet.safetynet.repository.FirestationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class FirestationService {

    private final FirestationRepository firestationRepository;

    public Firestation addFirestation(Firestation firestation) {
        return firestationRepository.addFirestation(firestation);
    }

    public Firestation updateFirestation(String address, Firestation updatedFirestation) {
        return firestationRepository.updateFirestation(address, updatedFirestation);
    }

    public boolean deleteFirestation(String address) {
        return firestationRepository.deleteFirestation(address);
    }

    public List<Firestation> getAllFirestations() {
        return firestationRepository.getAllFirestations();
    }
}
