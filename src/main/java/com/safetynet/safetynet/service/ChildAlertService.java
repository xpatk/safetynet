package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.ChildAlertDTO;
import com.safetynet.safetynet.dto.ChildAlertDTO.ChildInfo;
import com.safetynet.safetynet.dto.ChildAlertDTO.HouseholdMember;
import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ChildAlertService {

    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    /**
     * Get children and household members living at a given address.
     *
     * @param address The address to search.
     * @return A DTO with children and household members. Empty if no children found.
     */
    public ChildAlertDTO getChildrenAtAddress(String address) {
        log.debug("Starting getChildrenAtAddress for address: {}", address);

        // Get all persons at the given address
        List<Person> personsAtAddress = personService.getAllPersons().stream()
                .filter(person -> address.equals(person.getAddress()))
                .toList();

        log.debug("Found {} persons at address {}", personsAtAddress.size(), address);

        List<MedicalRecord> allMedicalRecords = medicalRecordService.getAllMedicalRecords();

        List<ChildInfo> children = new ArrayList<>();
        List<HouseholdMember> householdMembers = new ArrayList<>();

        for (Person person : personsAtAddress) {
            MedicalRecord record = allMedicalRecords.stream()
                    .filter(r -> r.getFirstName().equals(person.getFirstName())
                            && r.getLastName().equals(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record == null) continue;

            int age = calculateAge(record.getBirthdate());

            if (age <= 18) {
                children.add(new ChildInfo(person.getFirstName(), person.getLastName(), age));
            }

            // Add every person who is not already a child as household member
            HouseholdMember member = new HouseholdMember(person.getFirstName(), person.getLastName());
            if (!children.stream().anyMatch(c -> c.getFirstName().equals(member.getFirstName())
                    && c.getLastName().equals(member.getLastName()))
                    && !householdMembers.contains(member)) {
                householdMembers.add(member);
            }
        }

        log.debug("Children found: {}", children.size());
        log.debug("Household members found: {}", householdMembers.size());

        if (children.isEmpty()) {
            log.debug("No children found at address: {}", address);
            return new ChildAlertDTO(new ArrayList<>(), new ArrayList<>());
        }

        return new ChildAlertDTO(children, householdMembers);
    }

    /**
     * Utility to calculate age from a birthdate.
     */
    int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
