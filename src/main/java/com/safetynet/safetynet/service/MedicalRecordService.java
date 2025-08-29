package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class for managing medical records
 */

@Service
@RequiredArgsConstructor
@Slf4j

public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    /**
     * GET all medical records.
     *
     * @return list of all medical records
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        log.debug("Fetching all medical records...");
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getAllMedicalRecords();
        log.debug("Finished retrieving records.");
        return medicalRecords;
    }


    /**
     * ADD a new medical record
     *
     * @param medicalRecord the medical record to add
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        log.debug("Adding medical record: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        medicalRecordRepository.addMedicalRecord(medicalRecord);
    }

    /**
     * Update an existing medical record.
     *
     * @param firstName  first name
     * @param lastName last name
     * @param updated the updated medical record data
     * @return updated MedicalRecord if successful; null otherwise
     */
    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updated) {
        log.debug("Updating medical record for: {} {}", firstName, lastName);
        return medicalRecordRepository.updateMedicalRecord(firstName, lastName, updated);
    }

    /**
     * Delete a medical record by first and last name.
     *
     * @param firstName the patient's first name
     * @param lastName the patient's last name
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        log.debug("Deleting medical record for: {} {}", firstName, lastName);
        return medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
    }

    /**
     * Find a medical record by first and last name.
     *
     * @param firstName the patient's first name
     * @param lastName the patient's last name
     * @return MedicalRecord if found; null otherwise
     */
    public MedicalRecord findByName(String firstName, String lastName) {
        log.debug("Finding medical record for: {} {}", firstName, lastName);
        return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}