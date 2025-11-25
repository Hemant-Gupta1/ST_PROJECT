package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Boundary-focused test suite verifying limits, edge cases,
 * and mutation-sensitive logic across different modules.
 */
class BoundarySniperTest {

    // --- FACULTY TENURE BOUNDARY ---
    @Test
    void testTenureExactBoundary() {
        // Faculty with a valid department
        Faculty f = new Faculty("F1", "T", "N", "e@e.com", 40, "Dept", 1000);
        
        // Simulate exactly 5 service years (should NOT grant tenure)
        for (int i = 0; i < 5; i++) {
            f.incrementServiceYears();
        }
        assertFalse(f.isTenured(), "5 years should not trigger tenure");
        
        // Sixth year triggers tenure (> 5 rule)
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

        // Returning on Day 15 = kept for exactly 14 days -> no fine
        double fineAt14 = lib.returnBook("1", 15);
        assertEquals(0.0, fineAt14, 0.01, "14 days kept should have 0 fine");

        // Re-borrow another book and keep 15 days -> overdue by 1 day
        LibraryBook b2 = new LibraryBook("2", "T2", "A2");
        lib.addBook(b2);
        lib.checkoutBook(s, "2", 1);

        double fineAt15 = lib.returnBook("2", 16);
        assertEquals(0.50, fineAt15, 0.01, "15 days kept should have 0.50 fine");
    }

    // --- AGE BOUNDARY (commented intentionally) ---
    // Ensures validation of lower/upper age limits
    // @Test
    // void testAgeExactBoundaries() {
    //     assertTrue(ValidationUtils.isValidAge(16), "16 should be valid");
    //     assertFalse(ValidationUtils.isValidAge(15), "15 should be invalid");
    //     assertTrue(ValidationUtils.isValidAge(100), "100 should be valid");
    //     assertFalse(ValidationUtils.isValidAge(101), "101 should be invalid");
    // }

    // --- DEPARTMENT SALARY MATH ---
    @Test
    void testDepartmentSalarySummationWithDifferentValues() {
        Department d = new Department("D", "CS", 10000);

        // Two faculty with different salaries
        Faculty f1 = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);
        Faculty f2 = new Faculty("2", "C", "D", "e@e.com", 30, "Dept", 3000);

        d.addFaculty(f1);
        d.addFaculty(f2);

        // Salary sum should equal 4000
        assertEquals(4000.0, d.calculateTotalSalaryExpense());
    }

    // --- COURSE CAPACITY BOUNDARY ---
    @Test
    void testCourseExactCapacity() {
        Course c = new Course("C1", "N", 3, 2); // capacity = 2
        Student s1 = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Student s2 = new Student("S2", "C", "D", "e@e.com", 20, "M");
        Student s3 = new Student("S3", "E", "F", "e@e.com", 20, "M");

        // First two enrollments succeed
        assertTrue(c.enroll(s1));
        assertTrue(c.enroll(s2));

        // Third should fail because capacity reached
        assertFalse(c.enroll(s3));

        // Confirm course is marked as full
        assertTrue(c.isFull());
    }
}
