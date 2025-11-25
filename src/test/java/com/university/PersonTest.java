package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Person abstract class using a small test subclass.
 * Validates getters, setters, and base-class validation logic.
 */
class PersonTest {

    // Simple concrete subclass to allow instantiation of abstract Person
    private static class TestPerson extends Person {
        public TestPerson(String id, String f, String l, String e, int a) {
            super(id, f, l, e, a);
        }
    }

    @Test
    void testPersonGettersAndSetters() {
        // Create a sample person
        Person p = new TestPerson("P1", "John", "Doe", "j@d.com", 30);

        // Default address should be "Unknown"
        assertEquals("Unknown", p.getAddress());

        // Setting a valid address should update it
        p.setAddress("123 Main St");
        assertEquals("123 Main St", p.getAddress());

        // Empty or null address should be ignored (value unchanged)
        p.setAddress("");
        assertEquals("123 Main St", p.getAddress());
        p.setAddress(null);
        assertEquals("123 Main St", p.getAddress());

        // Validate getters for identity fields
        assertEquals("P1", p.getId());
        assertEquals("John", p.getFirstName());
        assertEquals("Doe", p.getLastName());
        assertEquals("j@d.com", p.getEmail());

        // Full name concatenation
        assertEquals("John Doe", p.getFullName());

        // Age getter
        assertEquals(30, p.getAge());
    }

    @Test
    void testEmailValidation() {
        // Invalid email should trigger validation exception
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("P2", "A", "B", "invalid-email", 20);
        });

        // Ensure error message indicates invalid email
        assertTrue(e.getMessage().contains("Invalid email"));
    }
}
