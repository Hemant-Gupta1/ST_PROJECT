package com.university;

import org.junit.jupiter.api.Test;

// import src.main.java.com.university.Student;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Student class focusing on:
 * - Probation boundary behavior
 * - GPA calculations
 * - Dean's List logic
 * - Age validation
 * - International status flag
 * - toString() format correctness
 */
class StudentTest {

    @Test
    void testProbationBoundaryNoCredits() {
        // Student starts with 0 GPA and 0 credits
        // Probation rule requires: (gpa < 2.0) AND (credits > 0)
        Student s = new Student("SNew", "New", "Student", "n@s.edu", 18, "Gen");

        // New student with 0 credits should NOT be on probation
        assertFalse(s.isProbation(), "New student with 0 credits should not be on probation");

        // Add 1 credit with a poor grade → now meets probation conditions
        s.updateAcademicRecord(1.0, 1);
        assertTrue(s.isProbation(), "Student with 1 credit and low GPA should be on probation");
    }

    @Test
    void testValidStudentCreation() {
        // Ensure constructor sets fields correctly
        Student s = new Student("S001", "Alice", "Smith", "alice@univ.edu", 20, "CS");

        assertNotNull(s);
        assertEquals("Alice Smith", s.getFullName());
        assertEquals("CS", s.getMajor());
    }

    @Test
    void testInvalidAgeThrowsException() {
        // Age < 16 should be rejected by Person constructor
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Student("S002", "Kid", "Boy", "kid@univ.edu", 15, "Math");
        });

        assertTrue(e.getMessage().contains("at least 16"));
    }

    @Test
    void testGpaCalculation() {
        Student s = new Student("S001", "Bob", "Jones", "bob@univ.edu", 21, "Physics");

        // First course: perfect grade, 3 credits
        s.updateAcademicRecord(4.0, 3);
        assertEquals(4.0, s.getGpa(), 0.001);

        // Second course: slightly lower grade → cumulative GPA should average
        s.updateAcademicRecord(3.0, 3);
        assertEquals(3.5, s.getGpa(), 0.001);
    }

    @Test
    void testDeansListLogic() {
        Student s = new Student("S003", "Charlie", "Day", "charlie@univ.edu", 22, "Arts");

        // Not enough credits for Dean's List
        s.updateAcademicRecord(4.0, 3);
        assertFalse(s.isDeansList());

        // Now meets both GPA and credit requirements
        s.updateAcademicRecord(4.0, 9);
        assertTrue(s.isDeansList());

        // Add failing grade to drop GPA below requirement
        s.updateAcademicRecord(0.0, 10);
        assertFalse(s.isDeansList());
    }

    @Test
    void testProbationLogic() {
        Student s = new Student("S004", "Dave", "Lo", "dave@univ.edu", 20, "History");

        // Poor GPA + credits > 0 → should be on probation
        s.updateAcademicRecord(1.5, 3);
        assertTrue(s.isProbation());
    }

    @Test
    void testToStringOutput() {
        // Ensures exact match against expected format
        Student s = new Student("S99", "Test", "User", "t@u.com", 20, "Bio");

        String output = s.toString();

        // Helps kill string-formatting mutants
        assertEquals("Student: Test User [Bio]", output);
    }

    @Test
    void testInternationalStatus() {
        Student s = new Student("S99", "Test", "User", "t@u.com", 20, "Bio");

        // Default value should be false
        assertFalse(s.isInternational());

        // Mutability of international flag
        s.setInternational(true);
        assertTrue(s.isInternational());
    }
}
