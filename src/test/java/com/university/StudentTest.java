package com.university;

import org.junit.jupiter.api.Test;

// import src.main.java.com.university.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testProbationBoundaryNoCredits() {
        // Student with 0.0 GPA but 0 credits
        // Logic: "gpa < 2.0 && totalCredits > 0"
        Student s = new Student("SNew", "New", "Student", "n@s.edu", 18, "Gen");
        
        // 0 credits, 0.0 GPA -> Should NOT be on probation
        assertFalse(s.isProbation(), "New student with 0 credits should not be on probation");
        
        // Now add 1 credit with bad grade
        s.updateAcademicRecord(1.0, 1);
        assertTrue(s.isProbation(), "Student with 1 credit and 1.0 GPA should be on probation");
    }
    @Test
    void testValidStudentCreation() {
        Student s = new Student("S001", "Alice", "Smith", "alice@univ.edu", 20, "CS");
        assertNotNull(s);
        assertEquals("Alice Smith", s.getFullName());
        assertEquals("CS", s.getMajor());
    }

    @Test
    void testInvalidAgeThrowsException() {
        // Mutation Killer: Checks boundary condition age < 16
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Student("S002", "Kid", "Boy", "kid@univ.edu", 15, "Math");
        });
        assertTrue(e.getMessage().contains("at least 16"));
    }

    @Test
    void testGpaCalculation() {
        Student s = new Student("S001", "Bob", "Jones", "bob@univ.edu", 21, "Physics");
        
        // First course: 4.0 grade, 3 credits
        s.updateAcademicRecord(4.0, 3);
        assertEquals(4.0, s.getGpa(), 0.001);

        // Second course: 3.0 grade, 3 credits. Avg should be 3.5
        s.updateAcademicRecord(3.0, 3);
        assertEquals(3.5, s.getGpa(), 0.001);
    }

    @Test
    void testDeansListLogic() {
        Student s = new Student("S003", "Charlie", "Day", "charlie@univ.edu", 22, "Arts");
        
        // Not enough credits yet
        s.updateAcademicRecord(4.0, 3); 
        assertFalse(s.isDeansList());

        // Enough credits, high GPA
        s.updateAcademicRecord(4.0, 9); // Total 12 credits
        assertTrue(s.isDeansList());
        
        // Lower GPA below 3.5
        s.updateAcademicRecord(0.0, 10); // Tanks the GPA
        assertFalse(s.isDeansList());
    }

    @Test
    void testProbationLogic() {
        Student s = new Student("S004", "Dave", "Lo", "dave@univ.edu", 20, "History");
        s.updateAcademicRecord(1.5, 3);
        assertTrue(s.isProbation());
    }
    @Test
    void testToStringOutput() {
        Student s = new Student("S99", "Test", "User", "t@u.com", 20, "Bio");
        String output = s.toString();
        // Assert EXACT format to kill string mutants
        assertEquals("Student: Test User [Bio]", output);
    }
    
    @Test
    void testInternationalStatus() {
        Student s = new Student("S99", "Test", "User", "t@u.com", 20, "Bio");
        assertFalse(s.isInternational());
        s.setInternational(true);
        assertTrue(s.isInternational());
    }
}