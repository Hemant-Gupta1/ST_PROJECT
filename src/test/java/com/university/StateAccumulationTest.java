package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StateAccumulationTest {

    // --- TARGET: STUDENT CREDIT ACCUMULATION ---
    @Test
    void testStudentCreditAccumulation() {
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        
        // First update: 3 credits
        s.updateAcademicRecord(4.0, 3);
        assertEquals(3, s.getTotalCredits());
        
        // Second update: 3 credits
        // Logic check: Should be 6 (accumulated), not 3 (replaced)
        s.updateAcademicRecord(4.0, 3);
        assertEquals(6, s.getTotalCredits(), "Credits should accumulate, not replace");
    }

    // --- TARGET: FACULTY YEAR INCREMENT ---
    @Test
    void testFacultyYearAccumulation() {
        Faculty f = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);
        
        f.incrementServiceYears(); // 1
        f.incrementServiceYears(); // 2
        f.incrementServiceYears(); // 3
        
        assertEquals(3, f.getYearsOfService(), "Years should count up correctly");
    }

    // --- TARGET: COURSE DECREMENT ---
    @Test
    void testCourseEnrollmentDecrement() {
        Course c = new Course("C1", "N", 3, 10);
        Student s1 = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Student s2 = new Student("S2", "C", "D", "e@e.com", 20, "M");
        
        c.enroll(s1);
        c.enroll(s2);
        // FIX: Use .getEnrolledStudents().size() instead of .getEnrolledCount()
        assertEquals(2, c.getEnrolledStudents().size());
        
        c.drop(s1);
        assertEquals(1, c.getEnrolledStudents().size(), "Count should decrease on drop");
        
        c.drop(s2);
        assertEquals(0, c.getEnrolledStudents().size());
    }

    // --- TARGET: LIBRARY DAMAGE ACCUMULATION ---
    @Test
    void testLibraryBookDamageAccumulation() {
        LibraryBook b = new LibraryBook("1", "T", "A");
        
        b.reportDamage(10);
        assertEquals(10, b.getDamageLevel());
        
        b.reportDamage(20);
        assertEquals(30, b.getDamageLevel(), "Damage should accumulate");
    }
    
    // --- TARGET: DEPARTMENT REMOVAL ---
    @Test
    void testDepartmentRemovalLogic() {
        Department d = new Department("D", "C", 1000);
        Faculty f = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);
        
        d.addFaculty(f);
        assertEquals(1, d.getFacultyList().size());
        
        d.removeFaculty(f);
        assertEquals(0, d.getFacultyList().size(), "Faculty should be removed from list");
    }
}