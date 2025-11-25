package com.university;

import org.junit.jupiter.api.Test;

// import src.main.java.com.university.Course;
// import src.main.java.com.university.Student;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Course class focusing on
 * capacity limits, validation, cancellation, and getters.
 */
class CourseTest {

    @Test
    void testCourseEnrollmentLimit() {
        // Create a course with capacity = 1
        Course c = new Course("CS101", "Intro", 3, 1);
        Student s1 = new Student("S1", "A", "B", "a@b.com", 20, "CS");
        Student s2 = new Student("S2", "C", "D", "c@d.com", 20, "CS");

        // First enrollment should succeed
        assertTrue(c.enroll(s1), "First student should succeed");

        // Second enrollment should fail (course full)
        assertFalse(c.enroll(s2), "Second student should fail (Capacity full)");

        // Course should report full
        assertTrue(c.isFull());
    }

    @Test
    void testInvalidCourseCreation() {
        // Null ID -> invalid
        assertThrows(IllegalArgumentException.class, () -> {
            new Course(null, "Name", 3, 30);
        });

        // Invalid credits (0)
        assertThrows(IllegalArgumentException.class, () -> {
            new Course("ID", "Name", 0, 30);
        });
    }

    @Test
    void testDropStudent() {
        Course c = new Course("CS102", "Data Structures", 4, 10);
        Student s1 = new Student("S1", "A", "B", "a@b.com", 20, "CS");

        // Enrollment reduces available seats
        c.enroll(s1);
        assertEquals(9, c.getAvailableSeats());

        // Dropping restores the seat
        c.drop(s1);
        assertEquals(10, c.getAvailableSeats());
    }

    @Test
    void testCourseCancellation() {
        Course c = new Course("CS99", "Old", 3, 10);

        // Initially active
        assertTrue(c.isActive());

        // Cancel course
        c.cancelCourse();
        assertFalse(c.isActive());

        // Enroll should fail once inactive
        Student s = new Student("S1", "A", "B", "a@b.com", 20, "CS");
        assertFalse(c.enroll(s));
    }

    @Test
    void testCourseGetters() {
        // Ensures getter methods work (helps mutation testing)
        Course c = new Course("ID", "Name", 3, 10);

        assertEquals("ID", c.getCourseId());
        assertEquals("Name", c.getCourseName());
        assertEquals(3, c.getCredits());
        assertEquals(10, c.getCapacity());

        // Instructor not yet assigned
        assertNull(c.getInstructor());
    }
}
