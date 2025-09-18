package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link Person} entities.
 * <p>
 * Provides CRUD endpoints to create, read, update, and delete persons.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    /**
     * Retrieves all persons.
     *
     * @return list of all persons
     */
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        log.info("Fetching all persons");
        return ResponseEntity.ok(personService.getAllPersons());
    }

    /**
     * Adds a new person.
     *
     * @param person the person to add
     * @return confirmation message
     */
    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        personService.addPerson(person);
        log.info("Person added: {} {}", person.getFirstName(), person.getLastName());
        return ResponseEntity.ok("Person created successfully.");
    }

    /**
     * Updates an existing person.
     *
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     * @param person    updated person details
     * @return confirmation message or 404 if not found
     */
    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody Person person) {

        boolean updated = personService.updatePerson(firstName, lastName, person) != null;
        if (!updated) {
            log.warn("Person not found: {} {}", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
        log.info("Person updated: {} {}", firstName, lastName);
        return ResponseEntity.ok("Person updated successfully.");
    }

    /**
     * Deletes a person.
     *
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     * @return confirmation message or 404 if not found
     */
    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> deletePerson(
            @PathVariable String firstName,
            @PathVariable String lastName) {

        boolean deleted = personService.deletePerson(firstName, lastName);
        if (!deleted) {
            log.warn("Person not found for deletion: {} {}", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
        log.info("Person deleted: {} {}", firstName, lastName);
        return ResponseEntity.ok("Person deleted successfully.");
    }
}
