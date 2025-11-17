package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FacultyTest {

    @Test
    void testTenureEligibility() {
        Faculty f = new Faculty("F1", "Prof", "X", "x@univ.edu", 40, "CS", 100000);
        
        // 5 years - not tenured yet
        for(int i=0; i<5; i++) f.incrementServiceYears();
        assertFalse(f.isTenured());

        // 6th year - should be tenured
        f.incrementServiceYears();
        assertTrue(f.isTenured());
    }

    @Test
    void testSalaryRaise() {
        Faculty f = new Faculty("F1", "Prof", "X", "x@univ.edu", 40, "CS", 1000);
        f.giveRaise(10.0); // 10% raise
        assertEquals(1100.0, f.getSalary(), 0.01);
    }

    @Test
    void testInvalidDepartment() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Faculty("F1", "P", "X", "x@u.edu", 40, "C", 5000); // Dept "C" too short
        });
    }
}