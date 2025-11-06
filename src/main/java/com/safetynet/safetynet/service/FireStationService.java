package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class for managing {@link FireStation} entities.
 * Provides methods for adding, updating, deleting, and retrieving fire station mappings.
 * This class acts as an intermediary between the controller layer and the repository layer,
 * handling the business logic related to fire station data.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FireStationService {

    private final FireStationRepository firestationRepository;

    /**
     * Adds a new fire station mapping.
     *
     * @param firestation the {@link FireStation} entity to add
     */
    public void addFireStation(FireStation firestation) {
        log.debug("Adding fire station at address: {}", firestation.getAddress());
        firestationRepository.addFireStation(firestation);
    }

    /**
     * Updates the fire station mapping for a given address.
     *
     * @param address            the address to update
     * @param updatedFireStation the {@link FireStation} containing updated data
     * @return the updated {@link FireStation}, or {@code null} if not found
     */
    public FireStation updateFireStation(String address, FireStation updatedFireStation) {
        log.debug("Updating fire station at address: {}", address);
        return firestationRepository.updateFireStation(address, updatedFireStation);
    }

    /**
     * Deletes a fire station mapping by its address.
     *
     * @param address the address of the fire station to delete
     * @return {@code true} if the mapping was deleted successfully; {@code false} otherwise
     */
    public boolean deleteFireStation(String address) {
        log.debug("Deleting fire station at address: {}", address);
        return firestationRepository.deleteFireStation(address);
    }

    /**
     * Retrieves all fire station mappings.
     *
     * @return a list of all {@link FireStation} mappings
     */
    public List<FireStation> getAllFireStations() {
        log.debug("Fetching all fire stations");
        return firestationRepository.getAllFireStations();
    }
}
