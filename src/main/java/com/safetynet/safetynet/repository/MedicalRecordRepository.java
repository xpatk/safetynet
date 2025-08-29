package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.MedicalRecord;
import java.util.List;

public interface MedicalRecordRepository {
    void addMedicalRecord(MedicalRecord record);
    MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord);
    boolean deleteMedicalRecord(String firstName, String lastName);
    List<MedicalRecord> getAllMedicalRecords();
    MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);
}
