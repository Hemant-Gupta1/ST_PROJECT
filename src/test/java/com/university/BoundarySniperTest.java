package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoundarySniperTest {

    // --- FACULTY TENURE BOUNDARY ---
    @Test
    void testTenureExactBoundary() {
        // Fixed: Changed "D" to "Dept" to pass validation
        Faculty f = new Faculty("F1", "T", "N", "e@e.com", 40, "Dept", 1000);
        
        // Simulate exactly 5 years of service
        for (int i = 0; i < 5; i++) {
            f.incrementServiceYears();
        }
        
        // Should NOT be tenured yet (Needs > 5)
        assertFalse(f.isTenured(), "5 years should not trigger tenure");
        
        // One more year -> 6 years
        f.incrementServiceYears();
        assertTrue(f.isTenured(), "6 years should trigger tenure");
    }

    // --- LIBRARY FINE BOUNDARY ---
    @Test
    void testLibraryFineExactBoundary() {
        LibrarySystem lib = new LibrarySystem();
        Student s = new Student("S1", "A", "B", "a@b.com", 20, "CS");
        LibraryBook b = new LibraryBook("1", "T", "A");
        lib.addBook(b);
        
        // Borrow on Day 1
        lib.checkoutBook(s, "1", 1);
        
        // Return on Day 15 (Kept for exactly 14 days)
        // 14 > 14 is False. Fine should be 0.0
        double fineAt14 = lib.returnBook("1", 15);
        assertEquals(0.0, fineAt14, 0.01, "14 days kept should have 0 fine");
        
        // Re-borrow and return on Day 16 (Kept for 15 days)
        // 15 > 14 is True. Overdue by 1. Fine = 0.50 * 1
        LibraryBook b2 = new LibraryBook("2", "T2", "A2");
        lib.addBook(b2);
        lib.checkoutBook(s, "2", 1);
        
        double fineAt15 = lib.returnBook("2", 16);
        assertEquals(0.50, fineAt15, 0.01, "15 days kept should have 0.50 fine");
    }

    // --- AGE BOUNDARY ---
    @Test
    void testAgeExactBoundaries() {
        assertTrue(ValidationUtils.isValidAge(16), "16 should be valid");
        assertFalse(ValidationUtils.isValidAge(15), "15 should be invalid");
        assertTrue(ValidationUtils.isValidAge(100), "100 should be valid");
        assertFalse(ValidationUtils.isValidAge(101), "101 should be invalid");
    }

    // --- DEPARTMENT SALARY MATH ---
    @Test
    void testDepartmentSalarySummationWithDifferentValues() {
        Department d = new Department("D", "CS", 10000);
        // Fixed: Changed "D" to "Dept" to pass validation
        Faculty f1 = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);
        Faculty f2 = new Faculty("2", "C", "D", "e@e.com", 30, "Dept", 3000); 
        
        d.addFaculty(f1);
        d.addFaculty(f2);
        
        assertEquals(4000.0, d.calculateTotalSalaryExpense());
    }

    // --- COURSE CAPACITY BOUNDARY ---
    @Test
    void testCourseExactCapacity() {
        Course c = new Course("C1", "N", 3, 2); // Capacity 2
        Student s1 = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Student s2 = new Student("S2", "C", "D", "e@e.com", 20, "M");
        Student s3 = new Student("S3", "E", "F", "e@e.com", 20, "M");

        assertTrue(c.enroll(s1)); 
        assertTrue(c.enroll(s2)); 
        assertFalse(c.enroll(s3)); 
        
        assertTrue(c.isFull());
    }
}