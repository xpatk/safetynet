package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.repository.DataLoader;
import com.safetynet.safetynet.repository.FireStationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {

    private final DataLoader dataLoader;

    public FireStationRepositoryImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public List<FireStation> getAllFireStations() {
        return dataLoader.getFirestations();
    }

    @Override
    public FireStation addFireStation(FireStation firestation) {
        return null;
    }

    @Override
    public FireStation updateFireStation(String address, FireStation updatedFirestation) {
        return null;
    }

    @Override
    public FireStation addFirestation(FireStation firestation) {
        List<FireStation> firestations = dataLoader.getFirestations();
        firestations.add(firestation);
        dataLoader.setFireStations(firestations); // persist to file
        return firestation;
    }

    @Override
    public FireStation updateFirestation(String address, FireStation updatedFirestation) {
        List<FireStation> firestations = dataLoader.getFirestations();
        for (FireStation current : firestations) {
            if (current.getAddress().equals(address)) {
                current.setStation(updatedFirestation.getStation());
                dataLoader.setFireStations(firestations); // persist to file
                return current;
            }
        }
        return null;
    }

    @Override
    public boolean deleteFireStation(String address) {
        List<FireStation> firestations = dataLoader.getFirestations();
        boolean removed = firestations.removeIf(f -> f.getAddress().equals(address));
        if (removed) {
            dataLoader.setFireStations(firestations); // persist to file
        }
        return removed;
    }
}