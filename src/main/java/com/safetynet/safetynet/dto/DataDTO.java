package com.safetynet.safetynet.dto;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.MedicalRecord;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataDTO {
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;
}
