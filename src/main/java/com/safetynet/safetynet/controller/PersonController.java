package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    // POST
    @PostMapping
    public void addPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }

    // PUT
    @PutMapping
    public void updatePerson(@RequestBody Person person) {
        personService.updatePerson(person.getFirstName(), person.getLastName(), person);
    }

    // DELETE
    @DeleteMapping
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personService.deletePerson(firstName, lastName);
    }

}
