package com.university;

import org.junit.jupiter.api.Test;

// import src.main.java.com.university.Course;
// import src.main.java.com.university.Student;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void testCourseEnrollmentLimit() {
        // Create a course with Capacity = 1
        Course c = new Course("CS101", "Intro", 3, 1);
        Student s1 = new Student("S1", "A", "B", "a@b.com", 20, "CS");
        Student s2 = new Student("S2", "C", "D", "c@d.com", 20, "CS");

        assertTrue(c.enroll(s1), "First student should succeed");
        assertFalse(c.enroll(s2), "Second student should fail (Capacity full)");
        assertTrue(c.isFull());
    }

    @Test
    void testInvalidCourseCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Course(null, "Name", 3, 30);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Course("ID", "Name", 0, 30); // Invalid credits
        });
    }

    @Test
    void testDropStudent() {
        Course c = new Course("CS102", "Data Structures", 4, 10);
        Student s1 = new Student("S1", "A", "B", "a@b.com", 20, "CS");
        
        c.enroll(s1);
        assertEquals(9, c.getAvailableSeats());
        
        c.drop(s1);
        assertEquals(10, c.getAvailableSeats());
    }
    @Test
    void testCourseCancellation() {
        Course c = new Course("CS99", "Old", 3, 10);
        assertTrue(c.isActive());
        
        c.cancelCourse();
        assertFalse(c.isActive());
        
        // Try enrolling in cancelled course (Should fail)
        Student s = new Student("S1", "A", "B", "a@b.com", 20, "CS");
        assertFalse(c.enroll(s));
    }
    
    @Test
    void testCourseGetters() {
        // Mutants often survive in getters if they are never called
        Course c = new Course("ID", "Name", 3, 10);
        assertEquals("ID", c.getCourseId());
        assertEquals("Name", c.getCourseName());
        assertEquals(3, c.getCredits());
        assertEquals(10, c.getCapacity());
        assertNull(c.getInstructor());
    }
}