package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests Faculty behavior:
 * - Tenure boundary conditions
 * - Salary raise logic
 * - Department validation
 */
class FacultyTest {

    @Test
    void testTenureEligibility() {
        // Create faculty with valid details
        Faculty f = new Faculty("F1", "Prof", "X", "x@univ.edu", 40, "CS", 100000);
        
        // First 5 years → should NOT trigger tenure
        for(int i = 0; i < 5; i++) f.incrementServiceYears();
        assertFalse(f.isTenured());

        // Sixth year → tenure should be granted
        f.incrementServiceYears();
        assertTrue(f.isTenured());
    }

    @Test
    void testSalaryRaise() {
        // Create faculty with starting salary
        Faculty f = new Faculty("F1", "Prof", "X", "x@univ.edu", 40, "CS", 1000);

        // Apply 10% raise
        f.giveRaise(10.0);

        // Validate salary updated correctly
        assertEquals(1100.0, f.getSalary(), 0.01);
    }

    @Test
    void testInvalidDepartment() {
        // Department name must be at least 2 characters
        assertThrows(IllegalArgumentException.class, () -> {
            new Faculty("F1", "P", "X", "x@u.edu", 40, "C", 5000);
        });
    }
}
