package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.repository.DataLoader;
import com.safetynet.safetynet.repository.MedicalRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final DataLoader dataLoader;

    public MedicalRecordRepositoryImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    private List<MedicalRecord> getMedicalRecords() {
        return dataLoader.getMedicalrecords();
    }

    @Override
    public void addMedicalRecord(MedicalRecord record) {
        List<MedicalRecord> records = getMedicalRecords();
        records.add(record);
        dataLoader.setMedicalRecords(records); // persist change
    }

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

    @Override
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> records = getMedicalRecords();
        boolean removed = records.removeIf(record ->
                record.getFirstName().equalsIgnoreCase(firstName) && record.getLastName().equalsIgnoreCase(lastName)
        );
        if (removed) {
            dataLoader.setMedicalRecords(records);
        }
        return removed;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return List.copyOf(getMedicalRecords());
    }

    @Override
    public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
        return getMedicalRecords().stream()
                .filter(r -> r.getFirstName().equalsIgnoreCase(firstName) && r.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}
