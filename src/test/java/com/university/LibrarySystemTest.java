package com.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibrarySystemTest {

    private LibrarySystem lib;
    private Student s;
    private LibraryBook b;

    @BeforeEach
    void setup() {
        lib = new LibrarySystem();
        s = new Student("S1", "A", "B", "a@b.com", 20, "CS");
        b = new LibraryBook("12345", "Java Book", "Author");
        lib.addBook(b);
    }

    @Test
    void testBorrowAndFineCalculation() {
        // Day 1: Borrow
        lib.checkoutBook(s, "12345", 1);
        
        // Day 10: Return (9 days kept). No Fine.
        double fine = lib.returnBook("12345", 10);
        assertEquals(0.0, fine);
    }

    @Test
    void testOverdueFine() {
        // Day 1: Borrow
        lib.checkoutBook(s, "12345", 1);
        
        // Day 20: Return (19 days kept). Max is 14. Overdue by 5 days.
        // Fine = 5 * 0.50 = 2.50
        double fine = lib.returnBook("12345", 20);
        assertEquals(2.50, fine, 0.01);
    }

    @Test
    void testProbationStudentCannotBorrow() {
        s.updateAcademicRecord(1.0, 10); // Force probation
        boolean success = lib.checkoutBook(s, "12345", 1);
        assertFalse(success);
    }
    @Test
    void testYearWrapAroundReturn() {
        // Borrow on Day 360 (late December)
        lib.checkoutBook(s, "12345", 360);
        
        // Return on Day 5 (January next year)
        // Logic: 5 - 360 = -355. -355 + 365 = 10 days kept.
        // 10 days < 14 max. Fine should be 0.0.
        double fine = lib.returnBook("12345", 5);
        assertEquals(0.0, fine);

        // Case 2: Overdue across year boundary
        // Borrow Day 360. Return Day 20 (Total 25 days). 
        // Overdue by 11 days (25 - 14). Fine = 11 * 0.50 = 5.5
        lib.checkoutBook(s, "12345", 360); // Re-borrow (assuming logic allows or reset)
        // Note: In current logic, simple re-borrow might fail if not returned, 
        // so let's use a new book or ensure return state.
        LibraryBook b2 = new LibraryBook("999", "New Book", "Auth");
        lib.addBook(b2);
        lib.checkoutBook(s, "999", 360);
        
        double fine2 = lib.returnBook("999", 20); 
        assertEquals(5.5, fine2, 0.01);
    }
}