package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.PersonInfoDTO;
import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.model.Person;
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
public class PersonInfoService {

    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    /**
     * Retrieves personal and medical information for all people with the given last name.
     *
     * @param lastName the last name to search for
     * @return a list of {@link PersonInfoDTO} objects containing detailed information
     */
    public List<PersonInfoDTO> getPersonInfoByLastName(String lastName) {
        log.debug("Starting getPersonInfoByLastName for last name: {}", lastName);

        // Get all persons matching the given last name
        List<Person> personsWithLastName = personService.getAllPersons().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .toList();
        log.debug("Found {} persons with last name '{}'", personsWithLastName.size(), lastName);

        // Get all medical records
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();

        // Map each person to a PersonInfoDTO
        List<PersonInfoDTO> personInfoList = personsWithLastName.stream()
                .map(person -> {
                    // Match medical record
                    MedicalRecord record = medicalRecords.stream()
                            .filter(mr -> mr.getFirstName().equals(person.getFirstName())
                                    && mr.getLastName().equals(person.getLastName()))
                            .findFirst()
                            .orElse(null);

                    int age = (record != null && record.getBirthdate() != null)
                            ? calculateAge(record.getBirthdate())
                            : 0;

                    return new PersonInfoDTO(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            age,
                            person.getEmail(),
                            record != null ? record.getMedications() : List.of(),
                            record != null ? record.getAllergies() : List.of()
                    );
                })
                .collect(Collectors.toList());

        log.debug("Finished getPersonInfoByLastName, returning {} DTOs", personInfoList.size());
        return personInfoList;
    }

    /**
     * Utility method to calculate age from a LocalDate birthdate.
     */
    private int calculateAge(LocalDate birthdate) {
        if (birthdate == null) return 0;
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
