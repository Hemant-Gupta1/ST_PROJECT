package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite designed to trigger exception paths across all classes.
 * Ensures constructors and methods correctly enforce validation rules.
 */
class ExceptionBoosterTest {

    // --- STUDENT EXCEPTIONS ---
    @Test
    void testStudentInvalidInputs() {
        // Empty/invalid first name → should throw
        assertThrows(IllegalArgumentException.class, () ->
            new Student("S1", "", "Last", "e@e.com", 20, "Major"));

        // Empty major → invalid
        assertThrows(IllegalArgumentException.class, () ->
            new Student("S1", "First", "Last", "e@e.com", 20, ""));

        // Invalid grade: negative
        Student s = new Student("S1", "F", "L", "e@e.com", 20, "M");
        assertThrows(IllegalArgumentException.class, () -> s.updateAcademicRecord(-1.0, 3));

        // Invalid grade: above 4.0
        assertThrows(IllegalArgumentException.class, () -> s.updateAcademicRecord(5.0, 3));

        // Invalid credits: negative
        assertThrows(IllegalArgumentException.class, () -> s.updateAcademicRecord(4.0, -3));
    }

    // --- FACULTY EXCEPTIONS ---
    @Test
    void testFacultyInvalidInputs() {
        // Negative base salary → should fail
        assertThrows(IllegalArgumentException.class, () ->
            new Faculty("F1", "F", "L", "e@e.com", 40, "Dept", -1000));

        // Negative raise percentage → invalid
        Faculty f = new Faculty("F1", "F", "L", "e@e.com", 40, "Dept", 1000);
        assertThrows(IllegalArgumentException.class, () -> f.giveRaise(-10));
    }

    // --- COURSE EXCEPTIONS ---
    @Test
    void testCourseInvalidInputs() {
        // Null ID → invalid
        assertThrows(IllegalArgumentException.class, () ->
            new Course(null, "Name", 3, 10));

        // Invalid credits: 0 or >6
        assertThrows(IllegalArgumentException.class, () -> new Course("C1", "N", 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Course("C1", "N", 7, 10));

        // Negative capacity → invalid
        assertThrows(IllegalArgumentException.class, () -> new Course("C1", "N", 3, -5));

        // Setting instructor to null → invalid
        Course c = new Course("C1", "N", 3, 10);
        assertThrows(IllegalArgumentException.class, () -> c.setInstructor(null));
    }

    // --- DEPARTMENT EXCEPTIONS ---
    @Test
    void testDepartmentInvalidInputs() {
        // Empty department name → invalid
        assertThrows(IllegalArgumentException.class, () -> new Department("", "C", 100));

        // Negative budget → invalid
        assertThrows(IllegalArgumentException.class, () -> new Department("N", "C", -100));

        // Assigning null head → invalid
        Department d = new Department("N", "C", 100);
        assertThrows(IllegalArgumentException.class, () -> d.assignHead(null));
    }

    // --- LIBRARY EXCEPTIONS ---
    @Test
    void testLibraryInvalidInputs() {
        LibrarySystem lib = new LibrarySystem();

        // Returning a non-existent book → invalid
        assertThrows(IllegalArgumentException.class, () -> lib.returnBook("99999", 1));

        // Null ISBN when creating a book → invalid
        assertThrows(IllegalArgumentException.class, () -> new LibraryBook(null, "T", "A"));
    }

    // --- PERSON (BASE CLASS) EXCEPTIONS ---
    @Test
    void testPersonBaseValidations() {
        // Invalid ID field → empty string
        assertThrows(IllegalArgumentException.class, () ->
            new Student("", "F", "L", "e@e.com", 20, "M"));

        // Invalid email format → missing '@' or '.'
        assertThrows(IllegalArgumentException.class, () ->
            new Student("S1", "F", "L", "bad-email", 20, "M"));

        // Invalid age → less than required minimum (16)
        assertThrows(IllegalArgumentException.class, () ->
            new Student("S1", "F", "L", "e@e.com", 12, "M"));
    }
}
