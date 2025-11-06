package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.repository.DataLoader;
import com.safetynet.safetynet.repository.FireStationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link FireStationRepository} interface.
 * Provides data access methods for managing {@link FireStation} entities
 * using the in-memory data loaded by {@link DataLoader}.
 */
@Repository
public class FireStationRepositoryImpl implements FireStationRepository {

    private final DataLoader dataLoader;

    /**
     * Constructs a new {@code FireStationRepositoryImpl} with the specified data loader.
     *
     * @param dataLoader the {@link DataLoader} responsible for loading and saving data
     */
    public FireStationRepositoryImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    /**
     * Retrieves all fire station mappings.
     *
     * @return a list of all {@link FireStation} objects
     */
    @Override
    public List<FireStation> getAllFireStations() {
        return dataLoader.getFireStations();
    }

    /**
     * Adds a new fire station mapping and persists the updated list.
     *
     * @param fireStation the {@link FireStation} to add
     */
    @Override
    public void addFireStation(FireStation fireStation) {
        List<FireStation> fireStations = dataLoader.getFireStations();
        fireStations.add(fireStation);
        dataLoader.setFireStations(fireStations); // persist to file
    }

    /**
     * Updates the station number of an existing fire station mapping based on the given address.
     *
     * @param address             the address of the fire station mapping to update
     * @param updatedFireStation  the {@link FireStation} containing updated details
     * @return the updated {@link FireStation}, or {@code null} if no matching address was found
     */
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

    /**
     * Deletes a fire station mapping for the specified address.
     *
     * @param address the address of the fire station mapping to delete
     * @return {@code true} if the mapping was successfully deleted; {@code false} otherwise
     */
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
