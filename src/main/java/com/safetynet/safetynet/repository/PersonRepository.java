package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.Person;

import java.util.List;

public interface PersonRepository {

    Person addPerson(Person person);

    Person updatePerson(String firstName, String lastName, Person updatedPerson);

    boolean deletePerson(String firstName, String lastName);

    List<Person> getAllPersons();
}
