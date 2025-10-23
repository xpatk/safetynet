package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest {

    @MockitoBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    private Person johnDoe;

    @BeforeEach
    void setUp() {
        johnDoe = new Person("John", "Doe", "123 Main St", "City", "11111", "john@doe.com", "123-456-7890");
    }

    @Test
    void testAddPerson_Success() {
        when(personRepository.addPerson(johnDoe)).thenReturn(johnDoe);

        Person added = personService.addPerson(johnDoe);

        assertThat(added).isNotNull();
        assertThat(added.getFirstName()).isEqualTo("John");
        verify(personRepository, times(1)).addPerson(johnDoe);
    }

    @Test
    void testAddPerson_MissingFirstName() {
        johnDoe.setFirstName(null);

        assertThrows(IllegalArgumentException.class, () -> personService.addPerson(johnDoe));

        verify(personRepository, never()).addPerson(any());
    }

    @Test
    void testUpdatePerson_Success() {
        when(personRepository.updatePerson("John", "Doe", johnDoe)).thenReturn(johnDoe);

        Person updated = personService.updatePerson("John", "Doe", johnDoe);

        assertThat(updated).isNotNull();
        assertThat(updated.getLastName()).isEqualTo("Doe");
        verify(personRepository, times(1)).updatePerson("John", "Doe", johnDoe);
    }

    @Test
    void testUpdatePerson_NotFound() {
        when(personRepository.updatePerson("John", "Doe", johnDoe)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> personService.updatePerson("John", "Doe", johnDoe));

        verify(personRepository, times(1)).updatePerson("John", "Doe", johnDoe);
    }

    @Test
    void testDeletePerson_Success() {
        when(personRepository.deletePerson("John", "Doe")).thenReturn(true);

        boolean deleted = personService.deletePerson("John", "Doe");

        assertThat(deleted).isTrue();
        verify(personRepository, times(1)).deletePerson("John", "Doe");
    }

    @Test
    void testDeletePerson_NotFound() {
        when(personRepository.deletePerson("John", "Doe")).thenReturn(false);

        boolean deleted = personService.deletePerson("John", "Doe");

        assertThat(deleted).isFalse();
        verify(personRepository, times(1)).deletePerson("John", "Doe");
    }

    @Test
    void testGetAllPersons() {
        when(personRepository.getAllPersons()).thenReturn(List.of(johnDoe));

        List<Person> persons = personService.getAllPersons();

        assertThat(persons).hasSize(1);
        assertThat(persons.get(0).getFirstName()).isEqualTo("John");
        verify(personRepository, times(1)).getAllPersons();
    }
}
