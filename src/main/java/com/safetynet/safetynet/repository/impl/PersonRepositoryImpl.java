package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final List<Person> persons = new ArrayList<>();

    @Override
    public Person addPerson(Person person) {
        persons.add(person);
        return person;
    }

    @Override
    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        for (Person p : persons) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
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
        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Person> getAllPersons() {
        return new ArrayList<>(persons);
    }
}
