package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.PhoneAlertDTO;
import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneAlertService {

    private final FireStationService fireStationService;
    private final PersonService personService;

    /**
     * Retrieves the list of phone numbers of all residents
     * covered by a specific fire station.
     *
     * @param stationNumber The fire station number.
     * @return A {@link PhoneAlertDTO} containing the phone numbers.
     */
    public PhoneAlertDTO getPhonesByFireStation(String stationNumber) {
        log.debug("Fetching phone numbers for fire station: {}", stationNumber);

        // Get addresses served by the given fire station
        List<FireStation> fireStations = fireStationService.getAllFireStations();
        List<String> addresses = fireStations.stream()
                .filter(station -> station.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        log.debug("Addresses found for fire station {}: {}", stationNumber, addresses);

        // Get residents living at those addresses and collect their phone numbers
        List<Person> persons = personService.getAllPersons();
        List<String> phoneNumbers = persons.stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .collect(Collectors.toList());

        log.debug("Phone numbers retrieved for fire station {}: {}", stationNumber, phoneNumbers);

        return new PhoneAlertDTO(phoneNumbers);
    }
}
