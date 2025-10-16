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
        return dataLoader.getFireStations();
    }

    @Override
    public void addFireStation(FireStation fireStation) {
        List<FireStation> fireStations = dataLoader.getFireStations();
        fireStations.add(fireStation);
        dataLoader.setFireStations(fireStations); // persist to file
    }

    @Override
    public FireStation updateFireStation(String address, FireStation updatedFireStation) {
        List<FireStation> fireStations = dataLoader.getFireStations();
        for (FireStation current : fireStations) {
            if (current.getAddress().equalsIgnoreCase(address)) {
                current.setStation(updatedFireStation.getStation());
                dataLoader.setFireStations(fireStations); // persist to file
                return current;
            }
        }
        return null;
    }

    @Override
    public boolean deleteFireStation(String address) {
        List<FireStation> fireStations = dataLoader.getFireStations();
        boolean removed = fireStations.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
        if (removed) {
            dataLoader.setFireStations(fireStations); // persist to file
        }
        return removed;
    }
}
