package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    // Create a concrete implementation of the abstract class for testing
    private static class TestPerson extends Person {
        public TestPerson(String id, String f, String l, String e, int a) {
            super(id, f, l, e, a);
        }
    }

    @Test
    void testPersonGettersAndSetters() {
        Person p = new TestPerson("P1", "John", "Doe", "j@d.com", 30);
        
        // Test Default Address
        assertEquals("Unknown", p.getAddress());
        
        // Test Setter
        p.setAddress("123 Main St");
        assertEquals("123 Main St", p.getAddress());
        
        // Test Empty Setter (Should not change value)
        p.setAddress("");
        assertEquals("123 Main St", p.getAddress());
        p.setAddress(null);
        assertEquals("123 Main St", p.getAddress());
        
        // Test all other getters to ensure coverage
        assertEquals("P1", p.getId());
        assertEquals("John", p.getFirstName());
        assertEquals("Doe", p.getLastName());
        assertEquals("j@d.com", p.getEmail());
        assertEquals("John Doe", p.getFullName());
        assertEquals(30, p.getAge());
    }

    @Test
    void testEmailValidation() {
        // Boundary test for email
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("P2", "A", "B", "invalid-email", 20);
        });
        assertTrue(e.getMessage().contains("Invalid email"));
    }
}