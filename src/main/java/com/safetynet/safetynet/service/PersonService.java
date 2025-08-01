package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person addPerson(Person person) {
        return personRepository.addPerson(person);
    }

    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        return personRepository.updatePerson(firstName, lastName, updatedPerson);
    }

    public boolean deletePerson(String firstName, String lastName) {
        return personRepository.deletePerson(firstName, lastName);
    }

    public List<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }
}