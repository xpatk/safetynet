package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repository;

    public MedicalRecordService(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    public MedicalRecord add(MedicalRecord record) {
        return repository.addMedicalRecord(record);
    }

    public MedicalRecord update(String firstName, String lastName, MedicalRecord updated) {
        return repository.updateMedicalRecord(firstName, lastName, updated);
    }

    public boolean delete(String firstName, String lastName) {
        return repository.deleteMedicalRecord(firstName, lastName);
    }

    public List<MedicalRecord> findAll() {
        return repository.getAllMedicalRecords();
    }

    public MedicalRecord findByName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName);
    }
}