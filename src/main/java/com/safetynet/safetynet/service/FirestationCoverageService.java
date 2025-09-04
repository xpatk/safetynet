package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.FirestationCoverageDTO;
import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.model.MedicalRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirestationCoverageService {

    private final FirestationService firestationService;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    public FirestationCoverageDTO getCoverageByStation(String stationNumber) {
        // Get addresses for the given station
        List<String> addresses = firestationService.getAllFirestations().stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .toList();

        // Get all persons living at those addresses
        List<Person> persons = personService.getAllPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();

        // Map persons to DTO
        List<FirestationCoverageDTO.PersonInfoDTO> personInfoList = persons.stream()
                .map(p -> new FirestationCoverageDTO.PersonInfoDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        p.getPhone()
                ))
                .collect(Collectors.toList());

        // Count children
        long childCount = persons.stream()
                .filter(p -> {
                    MedicalRecord mr = medicalRecordService.findByName(p.getFirstName(), p.getLastName());
                    return mr != null && calculateAge(mr.getBirthdate()) <= 18;
                })
                .count();

        // Count adults
        long adultCount = persons.size() - childCount;

        return new FirestationCoverageDTO(personInfoList, adultCount, childCount);
    }

    private int calculateAge(String birthdate) {
        LocalDate birth = LocalDate.parse(birthdate);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
