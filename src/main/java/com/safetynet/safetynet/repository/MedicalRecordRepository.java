package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.MedicalRecord;
import java.util.List;

/**
 * Repository interface for managing {@link MedicalRecord} data.
 * Provides methods to add, update, delete, and retrieve medical records
 * from the data source.
 */
public interface MedicalRecordRepository {

    /**
     * Adds a new medical record to the data source.
     *
     * @param record the {@link MedicalRecord} to add
     */
    void addMedicalRecord(MedicalRecord record);

    /**
     * Updates an existing medical record identified by the specified first and last name.
     *
     * @param firstName     the first name of the person whose record will be updated
     * @param lastName      the last name of the person whose record will be updated
     * @param updatedRecord the {@link MedicalRecord} containing the updated details
     * @return the updated {@link MedicalRecord}, or {@code null} if no matching record was found
     */
    MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord);

    /**
     * Deletes the medical record of a person identified by the given first and last name.
     *
     * @param firstName the first name of the person whose record will be deleted
     * @param lastName  the last name of the person whose record will be deleted
     * @return {@code true} if the record was successfully deleted; {@code false} otherwise
     */
    boolean deleteMedicalRecord(String firstName, String lastName);

    /**
     * Retrieves all medical records from the data source.
     *
     * @return a list of all {@link MedicalRecord} objects
     */
    List<MedicalRecord> getAllMedicalRecords();

    /**
     * Finds a medical record by the person's first and last name.
     *
     * @param firstName the first name to search for
     * @param lastName  the last name to search for
     * @return the matching {@link MedicalRecord}, or {@code null} if no match is found
     */
    MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);
}
