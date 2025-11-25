package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests EnrollmentService logic including:
 * - Successful enrollment
 * - Handling of null inputs
 * - Probation restriction
 * - Course capacity checks
 * - Credit limit enforcement
 * - Withdrawal behavior
 */
class EnrollmentServiceTest {

    @Test
    void testEnrollmentSuccess() {
        // Basic enrollment scenario
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "Alice", "W", "a@u.edu", 20, "CS");
        Course c = new Course("CS101", "Java", 3, 10);

        // Student should successfully enroll
        assertTrue(service.enroll(s, c));
        assertTrue(c.getEnrolledStudents().contains(s));
    }

    @Test
    void testEnrollmentFailNulls() {
        // Ensures null checks work correctly
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "W", "a@u.edu", 20, "CS");
        Course c = new Course("CS101", "Java", 3, 10);

        assertFalse(service.enroll(null, c)); // Null student
        assertFalse(service.enroll(s, null)); // Null course
    }

    @Test
    void testEnrollmentFailProbation() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "Bad", "Std", "b@u.edu", 20, "CS");

        // Trigger probation: GPA < 2.0 and credits > 0
        s.updateAcademicRecord(1.0, 10);

        Course c = new Course("CS101", "Java", 3, 10);

        // Must reject enrollment for probation student
        assertFalse(service.enroll(s, c));

        // Ensure no accidental enrollment occurred
        assertEquals(0, c.getEnrolledStudents().size());
    }

    @Test
    void testEnrollmentFailCourseFull() {
        // Tests capacity restriction
        EnrollmentService service = new EnrollmentService();
        Student s1 = new Student("S1", "A", "B", "a@b.edu", 20, "CS");
        Student s2 = new Student("S2", "C", "D", "c@d.edu", 20, "CS");

        Course c = new Course("CS101", "Java", 3, 1); // Capacity = 1

        service.enroll(s1, c);

        // Second student should fail due to full capacity
        assertFalse(service.enroll(s2, c));
    }

    @Test
    void testEnrollmentFailCreditLimit() {
        // Tests the 20-credit maximum rule
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "Over", "Load", "o@l.edu", 20, "CS");

        // Student already has 18 credits
        s.updateAcademicRecord(4.0, 18);

        Course c = new Course("CS101", "Hard", 4, 10);

        // Attempting to exceed 20 credits should fail
        assertFalse(service.enroll(s, c));
    }
    
    @Test
    void testWithdrawal() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "a@b.edu", 20, "CS");
        Course c = new Course("CS101", "Java", 3, 10);

        // Successful enrollment then withdrawal
        service.enroll(s, c);
        assertTrue(service.withdraw(s, c, false));
        assertFalse(c.getEnrolledStudents().contains(s));

        // Withdrawal when not enrolled → should fail
        assertFalse(service.withdraw(s, c, false));

        // Null student should fail
        assertFalse(service.withdraw(null, c, false));
    }
}
