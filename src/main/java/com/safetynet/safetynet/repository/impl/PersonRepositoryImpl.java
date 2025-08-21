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
        getPersons().add(person);
        return person;
    }

    @Override
    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        for (Person p : getPersons()) {
            if (p.getFirstName().equalsIgnoreCase(firstName) &&
                    p.getLastName().equalsIgnoreCase(lastName)) {
                p.setAddress(updatedPerson.getAddress());
                p.setCity(updatedPerson.getCity());
                p.setZip(updatedPerson.getZip());
                p.setPhone(updatedPerson.getPhone());
                p.setEmail(updatedPerson.getEmail());
                return p;
            }
        }
        return null; // Not found
    }

    @Override
    public boolean deletePerson(String firstName, String lastName) {
        Iterator<Person> iterator = getPersons().iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.getFirstName().equalsIgnoreCase(firstName) &&
                    p.getLastName().equalsIgnoreCase(lastName)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Person> getAllPersons() {
        return List.copyOf(getPersons());
    }
}
