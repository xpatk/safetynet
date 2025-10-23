package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.CommunityEmailDTO;
import com.safetynet.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link CommunityEmailService}.
 * Verifies that the service correctly filters and retrieves
 * unique email addresses of persons living in a given city.
 */
@SpringBootTest
class CommunityEmailServiceTest {

    @MockitoBean
    private PersonService personService;

    @Autowired
    private CommunityEmailService communityEmailService;

    private List<Person> mockPersons;

    @BeforeEach
    void setUp() {
        mockPersons = List.of(
                new Person("John", "Doe", "1 Dover St", "Miami", "33101", "111-222-3333", "john@doe.com"),
                new Person("Jane", "Doe", "2 Palm Ave", "Miami", "33102", "111-222-3334", "jane@doe.com"),
                new Person("Bob", "Smith", "3 Ocean Dr", "Orlando", "33103", "111-222-3335", "bob@smith.com"),
                new Person("NoMail", "User", "5 Bay Rd", "Miami", "33104", "111-222-3336", null) // no email
        );
    }

    @Test
    void testGetEmailsByCity_WithMultipleValidEmails() {
        // GIVEN
        when(personService.getAllPersons()).thenReturn(mockPersons);

        // WHEN
        CommunityEmailDTO result = communityEmailService.getEmailsByCity("Miami");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo("Miami");
        assertThat(result.getEmails()).hasSize(2); // john@doe.com and jane@doe.com
        assertThat(result.getEmails()).containsExactlyInAnyOrder("john@doe.com", "jane@doe.com");
    }

    @Test
    void testGetEmailsByCity_NoMatchFound() {
        // GIVEN
        when(personService.getAllPersons()).thenReturn(mockPersons);

        // WHEN
        CommunityEmailDTO result = communityEmailService.getEmailsByCity("Tampa");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo("Tampa");
        assertThat(result.getEmails()).isEmpty();
    }

    @Test
    void testGetEmailsByCity_CaseInsensitive() {
        // GIVEN
        when(personService.getAllPersons()).thenReturn(mockPersons);

        // WHEN
        CommunityEmailDTO result = communityEmailService.getEmailsByCity("miami");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo("miami");
        assertThat(result.getEmails()).containsExactlyInAnyOrder("john@doe.com", "jane@doe.com");
    }
}
