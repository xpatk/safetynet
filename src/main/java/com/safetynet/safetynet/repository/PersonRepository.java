package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class PersonRepository {

    private final List<Person> persons = new ArrayList<>();

    public Person addPerson(Person person) {
        persons.add(person);
        return person;
    }

    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
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

    public List<Person> getAllPersons() {
        return new ArrayList<>(persons);
    }
}
