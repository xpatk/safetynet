package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.FireStationCoverageDTO;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.model.MedicalRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FireStationCoverageService {

    private final FireStationService firestationService;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    /**
     * Get coverage details for a fire station number.
     *
     * @param stationNumber The fire station number.
     * @return DTO with list of residents, number of adults and number of children.
     */
    public FireStationCoverageDTO getCoverageByStation(String stationNumber) {
        log.debug("Starting getCoverageByStation for station number: {}", stationNumber);

        // Get addresses covered by the station
        List<String> addresses = firestationService.getAllFireStations().stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(f -> f.getAddress())
                .toList();
        log.debug("Found {} addresses covered by station {}", addresses.size(), stationNumber);

        // Get all persons living at those addresses
        List<Person> persons = personService.getAllPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();
        log.debug("Found {} persons living at these addresses", persons.size());

        // Map persons to DTO
        List<FireStationCoverageDTO.PersonInfoDTO> personInfoList = persons.stream()
                .map(p -> new FireStationCoverageDTO.PersonInfoDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        p.getPhone()
                ))
                .collect(Collectors.toList());
        log.debug("Mapped persons to PersonInfoDTO list");

        // Count children
        long childCount = persons.stream()
                .filter(p -> {
                    MedicalRecord mr = medicalRecordService.findByName(p.getFirstName(), p.getLastName());
                    boolean isChild = mr != null && calculateAge(mr.getBirthdate()) <= 18;
                    if (mr == null) log.debug("No medical record found for {} {}", p.getFirstName(), p.getLastName());
                    return isChild;
                })
                .count();
        long adultCount = persons.size() - childCount;

        log.debug("Adult count: {}, Child count: {}", adultCount, childCount);

        FireStationCoverageDTO dto = new FireStationCoverageDTO(personInfoList, adultCount, childCount);
        log.debug("Finished getCoverageByStation for station {}: DTO ready", stationNumber);
        return dto;
    }

    /**
     * Utility to calculate age from LocalDate birthdate.
     */
    private int calculateAge(LocalDate birthdate) {
        if (birthdate == null) return 0;
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
