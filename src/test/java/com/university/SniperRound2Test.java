package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SniperRound2Test {

    // --- TARGET: DUPLICATE CHECK REMOVAL ---
    // Logic: if (!list.contains(x)) add(x)
    // Mutant: Removes the check, allowing duplicates.
    @Test
    void testDepartmentDuplicateFaculty() {
        Department d = new Department("Dept", "D", 1000);
        Faculty f = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);
        
        d.addFaculty(f);
        d.addFaculty(f); // Try adding again
        
        // Assert size is still 1. If mutant exists, size will be 2.
        assertEquals(1, d.getFacultyList().size(), "Should not allow duplicate faculty");
    }
    
    @Test
    void testDepartmentAddExistingCourse() {
        Department d = new Department("Dept", "D", 1000);
        Course c = new Course("C1", "N", 3, 10);
        
        d.addCourse(c);
        d.addCourse(c); // Duplicate
        
        assertEquals(1, d.getCourseList().size(), "Should not allow duplicate courses");
    }

    @Test
    void testCourseDuplicateEnrollment() {
        Course c = new Course("C1", "N", 3, 10);
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        
        assertTrue(c.enroll(s)); // First time
        assertFalse(c.enroll(s)); // Second time (should fail)
        assertEquals(1, c.getEnrolledStudents().size());
    }

    // --- TARGET: PARTIAL CONDITION REMOVAL ---
    // Logic: !email.contains("@") || !email.contains(".")
    // Mutant: Removes one of the checks (e.g., only checks @).
    @Test
    void testPersonEmailStructure() {
        // Test 1: Has @ but no dot (Should fail)
        // If the mutant deleted the dot check, this would wrongly pass.
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> 
            new Student("S1", "A", "B", "user@localhost", 20, "M"));
        
        // Test 2: Has dot but no @ (Should fail)
        Exception e2 = assertThrows(IllegalArgumentException.class, () -> 
            new Student("S1", "A", "B", "user.localhost", 20, "M"));
    }

    // --- TARGET: EXACT CREDIT BOUNDARY ---
    // Logic: if (credits > 20)
    // Mutant: if (credits >= 20)
    @Test
    void testEnrollmentCreditLimitExactBoundary() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        
        // Setup: Student has 17 credits
        s.updateAcademicRecord(4.0, 17);
        
        // Enroll in 3 credit course (Total = 20)
        // 20 is allowed. 21 is not.
        Course c = new Course("C1", "N", 3, 10);
        
        assertTrue(service.enroll(s, c), "Enrollment should succeed at exactly 20 credits");
        assertEquals(1, c.getEnrolledStudents().size());
    }
    
    // --- TARGET: VOID METHOD CALCULATIONS ---
    // Logic: salary += raise
    // Mutant: salary = raise
    @Test
    void testFacultyRaiseMathStrict() {
        Faculty f = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);
        
        f.giveRaise(10); // 10% raise -> 1100
        assertEquals(1100.0, f.getSalary(), 0.01);
        
        f.giveRaise(0); // 0% raise -> 1100 (Stays same)
        assertEquals(1100.0, f.getSalary(), 0.01);
    }
}