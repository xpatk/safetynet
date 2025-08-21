package com.safetynet.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.dto.DataDTO;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.model.MedicalRecord;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
@Getter
public class DataLoader {

    private static final String JSON_FILE_PATH = "src/main/resources/data.json";

    private List<Person> persons = Collections.emptyList();
    private List<Firestation> firestations = Collections.emptyList();
    private List<MedicalRecord> medicalrecords = Collections.emptyList();

    public DataLoader() {
        loadData();
    }

    private void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File(JSON_FILE_PATH);
            DataDTO dataDTO = objectMapper.readValue(jsonFile, DataDTO.class);

            persons = dataDTO.getPersons();
            firestations = dataDTO.getFirestations();
            medicalrecords = dataDTO.getMedicalrecords();

            log.info("✅ Data loaded successfully. {} persons, {} firestations, {} medical records",
                    persons.size(), firestations.size(), medicalrecords.size());
        } catch (IOException e) {
            log.error("Failed to load data: {}", e.getMessage());
        }
    }
}
