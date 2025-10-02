package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.FireDTO;
import com.safetynet.safetynet.dto.FireDTO.ResidentInfo;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FireAlertService {

    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;
    private final FireStationService fireStationService;

    /**
     * Get details of residents and the fire station number for a given address.
     *
     * @param address The address to search for.
     * @return A DTO with resident details and the associated fire station number.
     */
    public FireDTO getFireAlertByAddress(String address) {
        log.debug("Starting getFireAlertByAddress for address: {}", address);

        // Retrieve all persons living at this address
        List<Person> personsAtAddress = personService.getAllPersons().stream()
                .filter(person -> address.equals(person.getAddress()))
                .toList();
        log.debug("Found {} persons at address {}", personsAtAddress.size(), address);

        // Retrieve all medical records
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();

        // Find the fire station covering this address and convert to int
        int stationNumber = fireStationService.getAllFireStations().stream()
                .filter(station -> address.equals(station.getAddress()))
                .map(FireStation::getStation)   // returns String
                .findFirst()
                .map(s -> {
                    try {
                        return Integer.parseInt(s); // convert String -> int
                    } catch (NumberFormatException e) {
                        log.warn("Invalid fire station number '{}', defaulting to 0", s);
                        return 0;
                    }
                })
                .orElse(0); // default if no fire station found

        // Build the list of residents with detailed information
        List<ResidentInfo> residents = personsAtAddress.stream()
                .map(person -> {
                    MedicalRecord medicalRecord = medicalRecords.stream()
                            .filter(record -> record.getFirstName().equals(person.getFirstName())
                                    && record.getLastName().equals(person.getLastName()))
                            .findFirst()
                            .orElse(null);

                    if (medicalRecord != null) {
                        int age = calculateAge(medicalRecord.getBirthdate());
                        return new ResidentInfo(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getPhone(),
                                age,
                                medicalRecord.getMedications(),
                                medicalRecord.getAllergies()
                        );
                    }

                    // If no medical record found, return with age 0 and empty lists
                    return new ResidentInfo(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getPhone(),
                            0,
                            List.of(),
                            List.of()
                    );
                })
                .collect(Collectors.toList());

        FireDTO fireAlertDTO = new FireDTO(stationNumber, residents);
        log.debug("Finished getFireAlertByAddress for address {}: DTO successfully built", address);
        return fireAlertDTO;
    }

    /**
     * Utility method to calculate age from a birthdate (LocalDate version).
     *
     * @param birthDate The birthdate of the person.
     * @return Age in years.
     */
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
