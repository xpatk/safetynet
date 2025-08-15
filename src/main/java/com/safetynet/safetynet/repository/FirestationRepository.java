package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.Firestation;

import java.util.List;

public interface FirestationRepository {
    List<Firestation> getAllFirestations();
    Firestation addFirestation(Firestation firestation);
    Firestation updateFirestation(String address, Firestation updatedFirestation);
    boolean deleteFirestation(String address);
}
