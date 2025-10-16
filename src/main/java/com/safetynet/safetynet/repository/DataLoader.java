package com.safetynet.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.safetynet.dto.DataDTO;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.MedicalRecord;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Repository responsible for loading and persisting SafetyNet data
 * from/to the JSON file located in {@code src/main/resources/data.json}.
 * <p>
 * The {@link DataLoader} reads the JSON file at application startup
 * and stores the content (persons, fire stations, medical records)
 * in memory. Whenever updates occur through setters, the file is
 * rewritten to keep data persistence synchronized.
 */
@Slf4j
@Repository
@Getter
public class DataLoader {

    /** Default JSON file path containing application data. */
    private static final String JSON_FILE_PATH = "src/main/resources/data.json";

    /** List of all persons managed by the system. */
    private List<Person> persons;

    /** List of all fire stations and their assigned addresses. */
    private List<FireStation> fireStations;

    /** List of all medical records for the persons. */
    private List<MedicalRecord> medicalRecords;

    /**
     * Initializes the DataLoader by reading the JSON file
     * and deserializing its contents into in-memory lists.
     */

    public DataLoader() {
        loadData();
    }

    /**
     * Reads and deserializes data from the JSON file into
     * {@link DataDTO}, initializing persons, fire stations,
     * and medical records.
     */
    private void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            // Load JSON from resources folder
            InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("data.json");
            if (jsonStream == null) {
                log.error("data.json not found in resources!");
                return;
            }

            DataDTO dataDTO = objectMapper.readValue(jsonStream, DataDTO.class);
            persons = dataDTO.getPersons();
            fireStations = dataDTO.getFireStations();
            medicalRecords = dataDTO.getMedicalRecords();
            log.info("Data successfully loaded from data.json");
        } catch (IOException e) {
            log.error("Failed to load data: {}", e.getMessage());
        }
    }

    /**
     * Serializes and writes the current in-memory data to the JSON file.
     */
    private void writeDataToFile() {
        DataDTO dataDTO = new DataDTO(persons, fireStations, medicalRecords);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(JSON_FILE_PATH), dataDTO);
            log.info("Data successfully written to {}", JSON_FILE_PATH);
        } catch (IOException e) {
            log.error("Failed to write data to file: {}", e.getMessage());
            throw new RuntimeException("Error writing data to file", e);
        }
    }

    /**
     * Updates the list of persons and persists the changes to file.
     *
     * @param persons the updated list of persons
     */
    public void setPersons(List<Person> persons) {
        this.persons = persons;
        writeDataToFile();
    }

    /**
     * Updates the list of fire stations and persists the changes to file.
     *
     * @param fireStations the updated list of fire stations
     */
    public void setFireStations(List<FireStation> fireStations) {
        this.fireStations = fireStations;
        writeDataToFile();
    }

    /**
     * Updates the list of medical records and persists the changes to file.
     *
     * @param medicalRecords the updated list of medical records
     */
    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
        writeDataToFile();
    }
}
