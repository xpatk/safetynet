package com.safetynet.safetynet.service;

import com.safetynet.safetynet.dto.CommunityEmailDTO;
import com.safetynet.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link CommunityEmailService}.
 *
 * <p>Verifies that the service correctly retrieves unique email addresses
 * of persons living in a specified city, including handling of null emails
 * and case-insensitive city names.</p>
 */
@SpringBootTest
class CommunityEmailServiceTest {

    @MockitoBean
    private PersonService personService;

    @Autowired
    private CommunityEmailService communityEmailService;

    private List<Person> mockPersons;

    /**
     * Initializes mock person data before each test.
     */
    @BeforeEach
    void setUp() {
        mockPersons = List.of(
                new Person("John", "Doe", "1 Dover St", "Miami", "33101", "111-222-3333", "john@doe.com"),
                new Person("Jane", "Doe", "2 Palm Ave", "Miami", "33102", "111-222-3334", "jane@doe.com"),
                new Person("Bob", "Smith", "3 Ocean Dr", "Orlando", "33103", "111-222-3335", "bob@smith.com"),
                new Person("NoMail", "User", "5 Bay Rd", "Miami", "33104", "111-222-3336", null) // no email
        );
    }

    /**
     * Test retrieving emails from a city with multiple valid email addresses.
     * Ensures only valid emails are returned.
     */
    @Test
    void testGetEmailsByCity_WithMultipleValidEmails() {
        when(personService.getAllPersons()).thenReturn(mockPersons);

        CommunityEmailDTO result = communityEmailService.getEmailsByCity("Miami");

        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo("Miami");
        assertThat(result.getEmails()).hasSize(2);
        assertThat(result.getEmails()).containsExactlyInAnyOrder("john@doe.com", "jane@doe.com");
    }

    /**
     * Test retrieving emails for a city with no matching persons.
     * Should return an empty email list.
     */
    @Test
    void testGetEmailsByCity_NoMatchFound() {
        when(personService.getAllPersons()).thenReturn(mockPersons);

        CommunityEmailDTO result = communityEmailService.getEmailsByCity("Tampa");

        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo("Tampa");
        assertThat(result.getEmails()).isEmpty();
    }

    /**
     * Test that city name matching is case-insensitive.
     * Emails for "Miami" should be returned even if the input is lowercase.
     */
    @Test
    void testGetEmailsByCity_CaseInsensitive() {
        when(personService.getAllPersons()).thenReturn(mockPersons);

        CommunityEmailDTO result = communityEmailService.getEmailsByCity("miami");

        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo("miami");
        assertThat(result.getEmails()).containsExactlyInAnyOrder("john@doe.com", "jane@doe.com");
    }
}
