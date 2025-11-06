package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.repository.DataLoader;
import com.safetynet.safetynet.repository.MedicalRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link MedicalRecordRepository} interface.
 * Provides data access methods for managing {@link MedicalRecord} entities
 * using the in-memory data loaded and persisted through {@link DataLoader}.
 */
@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final DataLoader dataLoader;

    /**
     * Constructs a new {@code MedicalRecordRepositoryImpl} with the specified data loader.
     *
     * @param dataLoader the {@link DataLoader} responsible for loading and saving data
     */
    public MedicalRecordRepositoryImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    /**
     * Retrieves all medical records from the data source.
     * If no records are available, returns an empty list.
     *
     * @return a list of all {@link MedicalRecord} objects, or an empty list if none exist
     */
    private List<MedicalRecord> getMedicalRecords() {
        List<MedicalRecord> records = dataLoader.getMedicalRecords();
        return records != null ? records : new ArrayList<>();
    }

    /**
     * Adds a new medical record and persists the change.
     *
     * @param record the {@link MedicalRecord} to add
     */
    @Override
    public void addMedicalRecord(MedicalRecord record) {
        List<MedicalRecord> records = getMedicalRecords();
        records.add(record);
        dataLoader.setMedicalRecords(records); // persist change
    }

    /**
     * Updates an existing medical record identified by the given first and last name.
     * Updates include birthdate, medications, and allergies.
     *
     * @param firstName     the first name of the person whose record will be updated
     * @param lastName      the last name of the person whose record will be updated
     * @param updatedRecord the {@link MedicalRecord} containing updated details
     * @return the updated {@link MedicalRecord}, or {@code null} if no matching record was found
     */
    @Override
    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        List<MedicalRecord> records = getMedicalRecords();
        for (MedicalRecord record : records) {
            if (record.getFirstName().equals(firstName) && record.getLastName().equals(lastName)) {
                record.setBirthdate(updatedRecord.getBirthdate());
                record.setMedications(updatedRecord.getMedications());
                record.setAllergies(updatedRecord.getAllergies());
                dataLoader.setMedicalRecords(records); // persist change
                return record;
            }
        }
        return null;
    }

    /**
     * Deletes a medical record matching the specified first and last name.
     *
     * @param firstName the first name of the person whose record will be deleted
     * @param lastName  the last name of the person whose record will be deleted
     * @return {@code true} if the record was successfully deleted; {@code false} otherwise
     */
    @Override
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> records = getMedicalRecords();
        boolean removed = records.removeIf(record ->
                record.getFirstName().equalsIgnoreCase(firstName)
                        && record.getLastName().equalsIgnoreCase(lastName)
        );
        if (removed) {
            dataLoader.setMedicalRecords(records);
        }
        return removed;
    }

    /**
     * Retrieves all medical records as an unmodifiable copy of the current data set.
     *
     * @return an immutable list of {@link MedicalRecord} objects
     */
    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return List.copyOf(getMedicalRecords());
    }

    /**
     * Finds a medical record by first and last name.
     *
     * @param firstName the first name to search for
     * @param lastName  the last name to search for
     * @return the matching {@link MedicalRecord}, or {@code null} if no match is found
     */
    @Override
    public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
        return getMedicalRecords().stream()
                .filter(r -> r.getFirstName().equalsIgnoreCase(firstName)
                        && r.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}
