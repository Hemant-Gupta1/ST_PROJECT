package com.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests core features of LibrarySystem:
 * - Borrowing rules
 * - Fine calculation (normal + overdue)
 * - Probation restrictions
 * - Year wrap-around borrow/return logic
 */
class LibrarySystemTest {

    private LibrarySystem lib;
    private Student s;
    private LibraryBook b;

    @BeforeEach
    void setup() {
        // Initialize fresh library system and sample student/book for each test
        lib = new LibrarySystem();
        s = new Student("S1", "A", "B", "a@b.com", 20, "CS");
        b = new LibraryBook("12345", "Java Book", "Author");
        lib.addBook(b);
    }

    @Test
    void testBorrowAndFineCalculation() {
        // Borrow on Day 1
        lib.checkoutBook(s, "12345", 1);

        // Return on Day 10 → kept 9 days → no fine expected
        double fine = lib.returnBook("12345", 10);
        assertEquals(0.0, fine);
    }

    @Test
    void testOverdueFine() {
        // Borrow on Day 1
        lib.checkoutBook(s, "12345", 1);

        // Return on Day 20 → kept 19 days → overdue by 5
        // Fine should be 5 × 0.50 = 2.50
        double fine = lib.returnBook("12345", 20);
        assertEquals(2.50, fine, 0.01);
    }

    @Test
    void testProbationStudentCannotBorrow() {
        // Trigger probation by giving low GPA with credits
        s.updateAcademicRecord(1.0, 10);

        // Probation students cannot borrow any books
        boolean success = lib.checkoutBook(s, "12345", 1);
        assertFalse(success);
    }

    @Test
    void testYearWrapAroundReturn() {
        // === Case 1: Year wrap-around but still within allowed borrow period ===
        lib.checkoutBook(s, "12345", 360); // Borrow late in the year
        
        // Day 5 next year: (5 - 360 = -355 → +365 = 10 days kept)
        double fine = lib.returnBook("12345", 5);
        assertEquals(0.0, fine);

        // === Case 2: Year wrap-around with overdue ===
        lib.checkoutBook(s, "12345", 360); // Would fail if book is still borrowed

        // Use a new book to avoid borrow conflict
        LibraryBook b2 = new LibraryBook("999", "New Book", "Auth");
        lib.addBook(b2);

        lib.checkoutBook(s, "999", 360);

        // Return on Day 20 → total 25 days kept → overdue by 11
        // Fine = 11 × 0.50 = 5.50
        double fine2 = lib.returnBook("999", 20);
        assertEquals(5.5, fine2, 0.01);
    }
}
