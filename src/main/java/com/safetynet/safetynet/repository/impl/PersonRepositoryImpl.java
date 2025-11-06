package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.DataLoader;
import com.safetynet.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link PersonRepository} interface.
 * <p>
 * This class provides CRUD operations for {@link Person} data by interacting with
 * the {@link DataLoader}, which handles persistence of the data source.
 * </p>
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final DataLoader dataLoader;

    /**
     * Constructs a {@code PersonRepositoryImpl} with the specified {@link DataLoader}.
     *
     * @param dataLoader the {@link DataLoader} used to access and persist person data
     */
    public PersonRepositoryImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    /**
     * Retrieves the list of all persons from the data source.
     * If the data is unavailable, an empty list is returned instead of {@code null}.
     *
     * @return a list of {@link Person} objects, or an empty list if none are found
     */
    private List<Person> getPersons() {
        List<Person> persons = dataLoader.getPersons();
        return persons != null ? persons : List.of();
    }

    /**
     * Adds a new person to the data source.
     *
     * @param person the {@link Person} to add
     * @return the added {@link Person}
     */
    @Override
    public Person addPerson(Person person) {
        List<Person> persons = getPersons();
        persons.add(person);
        dataLoader.setPersons(persons);
        return person;
    }

    /**
     * Updates an existing person's information identified by their first and last name.
     *
     * @param firstName     the first name of the person to update
     * @param lastName      the last name of the person to update
     * @param updatedPerson the {@link Person} object containing updated details
     * @return the updated {@link Person}, or {@code null} if no matching record was found
     */
    @Override
    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        List<Person> persons = getPersons();
        for (Person p : persons) {
            if (p.getFirstName().equalsIgnoreCase(firstName) &&
                    p.getLastName().equalsIgnoreCase(lastName)) {

                p.setAddress(updatedPerson.getAddress());
                p.setCity(updatedPerson.getCity());
                p.setZip(updatedPerson.getZip());
                p.setPhone(updatedPerson.getPhone());
                p.setEmail(updatedPerson.getEmail());
                dataLoader.setPersons(persons);
                return p;
            }
        }
        return null;
    }

    /**
     * Deletes a person identified by their first and last name.
     *
     * @param firstName the first name of the person to delete
     * @param lastName  the last name of the person to delete
     * @return {@code true} if the person was successfully deleted; {@code false} otherwise
     */
    @Override
    public boolean deletePerson(String firstName, String lastName) {
        List<Person> persons = getPersons();
        boolean removed = persons.removeIf(p ->
                p.getFirstName().equalsIgnoreCase(firstName) &&
                        p.getLastName().equalsIgnoreCase(lastName)
        );
        if (removed) {
            dataLoader.setPersons(persons);
        }
        return removed;
    }

    /**
     * Retrieves all persons from the data source.
     *
     * @return an unmodifiable list of all {@link Person} objects, or an empty list if none exist
     */
    @Override
    public List<Person> getAllPersons() {
        List<Person> persons = getPersons();
        return persons != null ? List.copyOf(persons) : List.of();
    }
}
