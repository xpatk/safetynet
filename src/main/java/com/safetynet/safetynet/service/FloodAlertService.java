package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.FloodStationsDTO;
import com.safetynet.safetynet.dto.FloodStationsDTO.HouseholdInfo;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FloodAlertService {

    private final PersonService personService;
    private final FireStationService fireStationService;
    private final MedicalRecordService medicalRecordService;

    /**
     * Get households covered by a list of fire stations.
     *
     * @param stationNumbers List of fire station numbers.
     * @return DTO containing households grouped by address.
     */
    public FloodStationsDTO getHouseholdsByStations(List<String> stationNumbers) {
        log.debug("Fetching households for fire stations: {}", stationNumbers);

        // Get addresses covered by the stations
        List<String> coveredAddresses = fireStationService.getAllFireStations().stream()
                .filter(f -> stationNumbers.contains(f.getStation()))
                .map(FireStation::getAddress)
                .toList();

        log.debug("Covered addresses: {}", coveredAddresses);

        // Get persons living at these addresses
        List<Person> persons = personService.getAllPersons().stream()
                .filter(p -> coveredAddresses.contains(p.getAddress()))
                .toList();

        log.debug("Number of persons at covered addresses: {}", persons.size());

        // Get all medical records
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();

        // Group by address and map to HouseholdInfo
        Map<String, List<HouseholdInfo>> households = persons.stream()
                .collect(Collectors.groupingBy(
                        Person::getAddress,
                        Collectors.mapping(person -> {
                            MedicalRecord record = medicalRecords.stream()
                                    .filter(r -> r.getFirstName().equals(person.getFirstName())
                                            && r.getLastName().equals(person.getLastName()))
                                    .findFirst()
                                    .orElse(null);

                            int age = record != null ? calculateAge(record.getBirthdate()) : 0;

                            return new HouseholdInfo(
                                    person.getFirstName(),
                                    person.getLastName(),
                                    person.getPhone(),
                                    age,
                                    record != null ? record.getMedications() : List.of(),
                                    record != null ? record.getAllergies() : List.of()
                            );
                        }, Collectors.toList())
                ));

        log.debug("Households grouped by address: {}", households);

        return new FloodStationsDTO(households);
    }

    /** Calculate age from LocalDate birthdate */
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
