package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.DataLoader;
import com.safetynet.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final DataLoader dataLoader;

    public PersonRepositoryImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    private List<Person> getPersons() {
        return dataLoader.getPersons();
    }

    @Override
    public Person addPerson(Person person) {
        List<Person> persons = getPersons();
        persons.add(person);
        dataLoader.setPersons(persons);
        return person;
    }

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
                dataLoader.setPersons(persons); // persist change
                return p;
            }
        }
        return null; // Not found
    }

    @Override
    public boolean deletePerson(String firstName, String lastName) {
        List<Person> persons = getPersons();
        boolean removed = persons.removeIf(p ->
                p.getFirstName().equalsIgnoreCase(firstName) &&
                        p.getLastName().equalsIgnoreCase(lastName)
        );
        if (removed) {
            dataLoader.setPersons(persons); // persist change
        }
        return removed;
    }

    @Override
    public List<Person> getAllPersons() {
        return List.copyOf(getPersons());
    }
}
