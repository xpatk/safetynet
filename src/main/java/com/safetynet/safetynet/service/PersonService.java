package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PersonService {

    private List<Person> persons = new ArrayList<>();

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void updatePerson(Person updatedPerson) {
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            if (p.getFirstName().equals(updatedPerson.getFirstName()) && p.getLastName().equals(updatedPerson.getLastName())) {
                // Update other fields

                p.setAddress(updatedPerson.getAddress());
                p.setCity(updatedPerson.getCity());
                p.setZip(updatedPerson.getZip());
                p.setPhone(updatedPerson.getPhone());
                p.setEmail(updatedPerson.getEmail());
                return;
            }
        }
    }

    public void deletePerson(String firstName, String lastName) {
        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                iterator.remove();
                return;
            }
        }
    }

    public List<Person> getAllPersons() {
        return persons;
    }

}
