package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.repository.DataLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FireStationRepositoryImpl implements FirestationRepository {

    private final DataLoader dataLoader;

    public FireStationRepositoryImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public List<FireStation> getAllFirestations() {
        return dataLoader.getFirestations();
    }

    @Override
    public Firestation addFirestation(Firestation firestation) {
        List<Firestation> firestations = dataLoader.getFirestations();
        firestations.add(firestation);
        dataLoader.setFirestations(firestations); // persist to file
        return firestation;
    }

    @Override
    public Firestation updateFirestation(String address, Firestation updatedFirestation) {
        List<Firestation> firestations = dataLoader.getFirestations();
        for (Firestation current : firestations) {
            if (current.getAddress().equals(address)) {
                current.setStation(updatedFirestation.getStation());
                dataLoader.setFirestations(firestations); // persist to file
                return current;
            }
        }
        return null;
    }

    @Override
    public boolean deleteFirestation(String address) {
        List<Firestation> firestations = dataLoader.getFirestations();
        boolean removed = firestations.removeIf(f -> f.getAddress().equals(address));
        if (removed) {
            dataLoader.setFirestations(firestations); // persist to file
        }
        return removed;
    }
}