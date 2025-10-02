package com.safetynet.safetynet.dto;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) that aggregates the main entities
 * of the SafetyNet system. It groups together all persons,
 * fire stations, and medical records for data exchange
 * between the application layers.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO {
    /**
     * List of all persons managed by the system.
     */
    private List<Person> persons;
    /**
     * List of fire stations and their associated addresses.
     */
    private List<FireStation> firestations;
    /**
     * List of medical records containing personal health information.
     */
    private List<MedicalRecord> medicalrecords;
}
