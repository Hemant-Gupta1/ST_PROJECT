package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentServiceTest {

    @Test
    void testEnrollmentSuccess() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "Alice", "W", "a@u.edu", 20, "CS");
        Course c = new Course("CS101", "Java", 3, 10);

        assertTrue(service.enroll(s, c));
        assertTrue(c.getEnrolledStudents().contains(s));
    }

    @Test
    void testEnrollmentFailNulls() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "W", "a@u.edu", 20, "CS");
        Course c = new Course("CS101", "Java", 3, 10);

        assertFalse(service.enroll(null, c));
        assertFalse(service.enroll(s, null));
    }

    @Test
    void testEnrollmentFailProbation() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "Bad", "Std", "b@u.edu", 20, "CS");
        // Trigger Probation: Low GPA (<2.0) AND Credits > 0
        s.updateAcademicRecord(1.0, 10); 
        
        Course c = new Course("CS101", "Java", 3, 10);
        
        // This kills the mutant that removes the "isProbation" check
        assertFalse(service.enroll(s, c));
        assertEquals(0, c.getEnrolledStudents().size());
    }

    @Test
    void testEnrollmentFailCourseFull() {
        EnrollmentService service = new EnrollmentService();
        Student s1 = new Student("S1", "A", "B", "a@b.edu", 20, "CS");
        Student s2 = new Student("S2", "C", "D", "c@d.edu", 20, "CS");
        
        Course c = new Course("CS101", "Java", 3, 1); // Capacity 1
        
        service.enroll(s1, c);
        // This kills the mutant that removes the "isFull" check
        assertFalse(service.enroll(s2, c));
    }

    @Test
    void testEnrollmentFailCreditLimit() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "Over", "Load", "o@l.edu", 20, "CS");
        
        // Give student 18 credits
        s.updateAcademicRecord(4.0, 18);
        
        // Try adding 4 credits (Total 22 > 20)
        Course c = new Course("CS101", "Hard", 4, 10);
        
        // This kills the mutant that changes > 20 to >= 20 or removes the check
        assertFalse(service.enroll(s, c));
    }
    
    @Test
    void testWithdrawal() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "a@b.edu", 20, "CS");
        Course c = new Course("CS101", "Java", 3, 10);
        
        service.enroll(s, c);
        assertTrue(service.withdraw(s, c, false)); // Withdraw before deadline
        assertFalse(c.getEnrolledStudents().contains(s));
        
        // Withdraw from course not enrolled in
        assertFalse(service.withdraw(s, c, false));
        
        // Withdraw null inputs
        assertFalse(service.withdraw(null, c, false));
    }
}