package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Mutation-focused test suite designed to kill mutants in:
 * - Duplicate prevention logic
 * - Email validation structure
 * - Credit limit boundary logic
 * - Salary raise arithmetic
 */
class SniperRound2Test {

    // --- TARGET: DUPLICATE CHECK REMOVAL ---
    // Ensures addFaculty does NOT add the same faculty twice.
    @Test
    void testDepartmentDuplicateFaculty() {
        Department d = new Department("Dept", "D", 1000);
        Faculty f = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);

        d.addFaculty(f);
        d.addFaculty(f); // Attempt duplicate insertion

        // If mutant removes the contains() check, list size becomes 2
        assertEquals(1, d.getFacultyList().size(), "Should not allow duplicate faculty");
    }

    @Test
    void testDepartmentAddExistingCourse() {
        Department d = new Department("Dept", "D", 1000);
        Course c = new Course("C1", "N", 3, 10);

        d.addCourse(c);
        d.addCourse(c); // Duplicate attempt

        // Ensures duplicate courses are not added
        assertEquals(1, d.getCourseList().size(), "Should not allow duplicate courses");
    }

    @Test
    void testCourseDuplicateEnrollment() {
        Course c = new Course("C1", "N", 3, 10);
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");

        // First enrollment succeeds
        assertTrue(c.enroll(s));

        // Second enrollment should fail (duplicate)
        assertFalse(c.enroll(s));
        assertEquals(1, c.getEnrolledStudents().size());
    }

    // --- TARGET: PARTIAL CONDITION REMOVAL ---
    // Ensures email requires BOTH '@' AND '.'
    @Test
    void testPersonEmailStructure() {
        // Case 1: Contains '@' but missing '.' → should fail
        Exception e1 = assertThrows(IllegalArgumentException.class, () ->
            new Student("S1", "A", "B", "user@localhost", 20, "M"));

        // Case 2: Contains '.' but missing '@' → should fail
        Exception e2 = assertThrows(IllegalArgumentException.class, () ->
            new Student("S1", "A", "B", "user.localhost", 20, "M"));
    }

    // --- TARGET: EXACT CREDIT BOUNDARY ---
    // Confirms that exactly 20 credits is allowed (mutant changes > to >=)
    @Test
    void testEnrollmentCreditLimitExactBoundary() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");

        // Student currently has 17 credits
        s.updateAcademicRecord(4.0, 17);

        // Adding 3-credit course → total 20 (valid)
        Course c = new Course("C1", "N", 3, 10);

        assertTrue(service.enroll(s, c), "Enrollment should succeed at exactly 20 credits");
        assertEquals(1, c.getEnrolledStudents().size());
    }

    // --- TARGET: VOID METHOD CALCULATIONS ---
    // Ensures salary raise uses addition, not assignment
    @Test
    void testFacultyRaiseMathStrict() {
        Faculty f = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);

        // 10% raise should increase salary to 1100
        f.giveRaise(10);
        assertEquals(1100.0, f.getSalary(), 0.01);

        // 0% raise should leave salary unchanged
        f.giveRaise(0);
        assertEquals(1100.0, f.getSalary(), 0.01);
    }
}
