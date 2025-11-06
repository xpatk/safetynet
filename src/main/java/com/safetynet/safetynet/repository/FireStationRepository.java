package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.FireStation;
import java.util.List;

/**
 * Repository interface for managing {@link FireStation} entities.
 * Defines CRUD operations for accessing and modifying fire station
 * mappings between addresses and station numbers.
 */
public interface FireStationRepository {

    /**
     * Retrieves all fire station mappings.
     *
     * @return a list of all {@link FireStation} objects
     */
    List<FireStation> getAllFireStations();

    /**
     * Adds a new fire station mapping.
     *
     * @param fireStation the {@link FireStation} to add
     */
    void addFireStation(FireStation fireStation);

    /**
     * Updates an existing fire station mapping based on the specified address.
     *
     * @param address the address of the fire station mapping to update
     * @param updatedFireStation the {@link FireStation} containing updated details
     * @return the updated {@link FireStation}, or {@code null} if no mapping was found for the given address
     */
    FireStation updateFireStation(String address, FireStation updatedFireStation);

    /**
     * Deletes a fire station mapping for the specified address.
     *
     * @param address the address of the fire station mapping to delete
     * @return {@code true} if the mapping was successfully deleted; {@code false} otherwise
     */
    boolean deleteFireStation(String address);
}
