package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    /**
     * Add a new person.
     * @param person The person to add.
     * @return The added person.
     */
    public Person addPerson(Person person) {
        log.debug("Adding person: {}", person);

        if (person.getFirstName() == null || person.getLastName() == null) {
            throw new IllegalArgumentException("First name and last name are required");
        }

        Person addedPerson = personRepository.addPerson(person);
        log.debug("Person successfully added: {}", addedPerson);
        return addedPerson;
    }

    /**
     * Update an existing person.
     * @param firstName The first name of the person to update.
     * @param lastName The last name of the person to update.
     * @param updatedPerson The updated person data.
     * @return The updated person.
     */
    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        log.debug("Updating person with first name: {}, last name: {} with details: {}", firstName, lastName, updatedPerson);

        Person updated = personRepository.updatePerson(firstName, lastName, updatedPerson);

        if (updated == null) {
            log.warn("Person not found for update: firstName={}, lastName={}", firstName, lastName);
            throw new RuntimeException("Person not found for update");
        }

        log.debug("Person successfully updated: {}", updated);
        return updated;
    }

    /**
     * Delete a person by first and last name.
     * @param firstName The first name.
     * @param lastName The last name.
     * @return true if deletion succeeded, false otherwise.
     */
    public boolean deletePerson(String firstName, String lastName) {
        log.debug("Deleting person: firstName={}, lastName={}", firstName, lastName);

        boolean isDeleted = personRepository.deletePerson(firstName, lastName);

        if (!isDeleted) {
            log.warn("Person not found for deletion: firstName={}, lastName={}", firstName, lastName);
            return false;
        }

        log.debug("Person successfully deleted: firstName={}, lastName={}", firstName, lastName);
        return true;
    }

    /**
     * Retrieve all persons.
     * @return A list of all persons.
     */
    public List<Person> getAllPersons() {
        log.debug("Retrieving all persons");
        List<Person> allPersons = personRepository.getAllPersons();
        log.debug("Total persons found: {}", allPersons.size());
        return allPersons;
    }
}
