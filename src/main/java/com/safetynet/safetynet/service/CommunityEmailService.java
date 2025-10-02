package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.CommunityEmailDTO;
import com.safetynet.safetynet.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CommunityEmailService {

    private final PersonService personService;

    /**
     * Get all email addresses of people living in a specified city.
     *
     * @param city The city to search for.
     * @return A DTO containing the city and a list of associated emails.
     */
    public CommunityEmailDTO getEmailsByCity(String city) {
        log.debug("Starting getEmailsByCity for city: {}", city);

        // Retrieve all persons
        List<Person> allPersons = personService.getAllPersons();
        log.debug("Total persons retrieved: {}", allPersons.size());

        // Filter persons by city and collect their emails
        List<String> emails = allPersons.stream()
                .filter(person -> city.equalsIgnoreCase(person.getCity()))
                .map(Person::getEmail)
                .filter(email -> email != null && !email.isEmpty()) // Exclude null or empty emails
                .distinct() // Remove duplicates
                .collect(Collectors.toList());

        log.debug("Emails retrieved for city {}: {}", city, emails);
        log.debug("Finished getEmailsByCity for city: {} with {} emails found.", city, emails.size());

        return new CommunityEmailDTO(city, emails);
    }
}
