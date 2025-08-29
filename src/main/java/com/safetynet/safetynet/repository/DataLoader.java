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
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;

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
        } catch (IOException e) {
            log.error("Failed to load data: {}", e.getMessage());
        }
    }

    private void writeDataToFile() {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setPersons(persons);
        dataDTO.setFirestations(firestations);
        dataDTO.setMedicalrecords(medicalrecords);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_PATH), dataDTO);
        } catch (IOException e) {
            log.error("Failed to write data: {}", e.getMessage());
            throw new RuntimeException("Error writing data to file", e);
        }
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
        writeDataToFile();
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
        writeDataToFile();
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
        writeDataToFile();
    }
}