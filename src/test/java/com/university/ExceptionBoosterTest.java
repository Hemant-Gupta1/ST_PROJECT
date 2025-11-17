package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionBoosterTest {

    // --- STUDENT EXCEPTIONS ---
    @Test
    void testStudentInvalidInputs() {
        // Test Null/Empty Name
        assertThrows(IllegalArgumentException.class, () -> 
            new Student("S1", "", "Last", "e@e.com", 20, "Major"));
        
        // Test Null Major
        assertThrows(IllegalArgumentException.class, () -> 
            new Student("S1", "First", "Last", "e@e.com", 20, ""));

        // Test Invalid Grade Input (Negative)
        Student s = new Student("S1", "F", "L", "e@e.com", 20, "M");
        assertThrows(IllegalArgumentException.class, () -> s.updateAcademicRecord(-1.0, 3));
        
        // Test Invalid Grade Input (Too High)
        assertThrows(IllegalArgumentException.class, () -> s.updateAcademicRecord(5.0, 3));
        
        // Test Negative Credits
        assertThrows(IllegalArgumentException.class, () -> s.updateAcademicRecord(4.0, -3));
    }

    // --- FACULTY EXCEPTIONS ---
    @Test
    void testFacultyInvalidInputs() {
        // Test Negative Salary
        assertThrows(IllegalArgumentException.class, () -> 
            new Faculty("F1", "F", "L", "e@e.com", 40, "Dept", -1000));

        // Test Negative Raise
        Faculty f = new Faculty("F1", "F", "L", "e@e.com", 40, "Dept", 1000);
        assertThrows(IllegalArgumentException.class, () -> f.giveRaise(-10));
    }

    // --- COURSE EXCEPTIONS ---
    @Test
    void testCourseInvalidInputs() {
        // Test Null ID
        assertThrows(IllegalArgumentException.class, () -> 
            new Course(null, "Name", 3, 10));
        
        // Test Invalid Credits (0 or >6)
        assertThrows(IllegalArgumentException.class, () -> new Course("C1", "N", 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Course("C1", "N", 7, 10));
        
        // Test Negative Capacity
        assertThrows(IllegalArgumentException.class, () -> new Course("C1", "N", 3, -5));
        
        // Test Null Instructor
        Course c = new Course("C1", "N", 3, 10);
        assertThrows(IllegalArgumentException.class, () -> c.setInstructor(null));
    }

    // --- DEPARTMENT EXCEPTIONS ---
    @Test
    void testDepartmentInvalidInputs() {
        // Test Empty Name
        assertThrows(IllegalArgumentException.class, () -> new Department("", "C", 100));
        
        // Test Negative Budget
        assertThrows(IllegalArgumentException.class, () -> new Department("N", "C", -100));
        
        // Test Null Head
        Department d = new Department("N", "C", 100);
        assertThrows(IllegalArgumentException.class, () -> d.assignHead(null));
    }

    // --- LIBRARY EXCEPTIONS ---
    @Test
    void testLibraryInvalidInputs() {
        LibrarySystem lib = new LibrarySystem();
        
        // Return book that doesn't exist
        assertThrows(IllegalArgumentException.class, () -> lib.returnBook("99999", 1));
        
        // Create book with nulls
        assertThrows(IllegalArgumentException.class, () -> new LibraryBook(null, "T", "A"));
    }
    
    // --- PERSON (BASE) EXCEPTIONS ---
    // This targets the specific validation in the abstract parent class
    @Test
    void testPersonBaseValidations() {
        // Invalid ID (Null/Empty)
        assertThrows(IllegalArgumentException.class, () -> 
            new Student("", "F", "L", "e@e.com", 20, "M"));
            
        // Invalid Email format
        assertThrows(IllegalArgumentException.class, () -> 
            new Student("S1", "F", "L", "bad-email", 20, "M"));
            
        // Invalid Age (<16)
        assertThrows(IllegalArgumentException.class, () -> 
            new Student("S1", "F", "L", "e@e.com", 12, "M"));
    }
}