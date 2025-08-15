package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final List<MedicalRecord> medicalRecords = new ArrayList<>();

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
        return record;
    }

    @Override
    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getFirstName().equals(firstName) && record.getLastName().equals(lastName)) {
                record.setBirthdate(updatedRecord.getBirthdate());
                record.setMedications(updatedRecord.getMedications());
                record.setAllergies(updatedRecord.getAllergies());
                return record;
            }
        }
        return null; // Not found
    }

    @Override
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        Iterator<MedicalRecord> iterator = medicalRecords.iterator();
        while (iterator.hasNext()) {
            MedicalRecord record = iterator.next();
            if (record.getFirstName().equals(firstName) && record.getLastName().equals(lastName)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return new ArrayList<>(medicalRecords);
    }

    @Override
    public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getFirstName().equals(firstName) && record.getLastName().equals(lastName)) {
                return record;
            }
        }
        return null;
    }
}
