package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.Person;
import java.util.List;

/**
 * Repository interface for managing {@link Person} data.
 * <p>
 * Provides CRUD operations to add, update, delete, and retrieve
 * person information from the data source.
 * </p>
 */
public interface PersonRepository {

    /**
     * Adds a new person to the data source.
     *
     * @param person the {@link Person} to add
     * @return the added {@link Person}
     */
    Person addPerson(Person person);

    /**
     * Updates an existing person’s details identified by their first and last name.
     *
     * @param firstName     the first name of the person to update
     * @param lastName      the last name of the person to update
     * @param updatedPerson the {@link Person} object containing the updated details
     * @return the updated {@link Person}, or {@code null} if no matching person was found
     */
    Person updatePerson(String firstName, String lastName, Person updatedPerson);

    /**
     * Deletes a person from the data source using their first and last name.
     *
     * @param firstName the first name of the person to delete
     * @param lastName  the last name of the person to delete
     * @return {@code true} if the person was successfully deleted; {@code false} otherwise
     */
    boolean deletePerson(String firstName, String lastName);

    /**
     * Retrieves all persons from the data source.
     *
     * @return a list of all {@link Person} objects
     */
    List<Person> getAllPersons();
}
