package com.university;

import org.junit.jupiter.api.Test;

// import src.main.java.com.university.LibraryBook;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests LibraryBook behavior including:
 * - Damage thresholds
 * - Damage capping
 * - Borrow/return logic
 * - Constructor validation
 */
class LibraryBookTest {

    @Test
    void testDamageBoundaries() {
        LibraryBook b = new LibraryBook("1", "T", "A");
        
        // Damage = 79 → still usable (boundary condition)
        b.reportDamage(79);
        assertTrue(b.isUsable());

        // Adding 1 more → 80 → no longer usable
        b.reportDamage(1);
        assertEquals(80, b.getDamageLevel());
        assertFalse(b.isUsable());
    }

    @Test
    void testDamageCapAt100() {
        LibraryBook b = new LibraryBook("1", "T", "A");

        b.reportDamage(50);
        b.reportDamage(60); // Total would be 110 without capping

        // Damage should cap at 100, not exceed it
        assertEquals(100, b.getDamageLevel());
    }

    @Test
    void testBookCreation() {
        // Basic constructor behavior
        LibraryBook b = new LibraryBook("123", "Title", "Author");

        assertEquals("123", b.getIsbn());
        assertEquals("Title", b.getTitle());
        assertEquals("Author", b.getAuthor());
        assertEquals(0, b.getDamageLevel());
        assertFalse(b.isBorrowed());
    }

    @Test
    void testInvalidBook() {
        // Null ISBN or null Title should throw exception
        assertThrows(IllegalArgumentException.class, () -> new LibraryBook(null, "T", "A"));
        assertThrows(IllegalArgumentException.class, () -> new LibraryBook("1", null, "A"));
    }

    @Test
    void testBorrowReturnFlow() {
        LibraryBook b = new LibraryBook("1", "T", "A");

        // Borrowing a book marks it as borrowed
        b.borrow();
        assertTrue(b.isBorrowed());

        // Borrowing again should cause an exception
        assertThrows(IllegalStateException.class, b::borrow);

        // Returning resets borrow state
        b.returnBook();
        assertFalse(b.isBorrowed());
    }

    @Test
    void testDamageLogic() {
        LibraryBook b = new LibraryBook("1", "T", "A");

        // Fresh book should be usable
        assertTrue(b.isUsable());

        // After 50 damage → still usable (<80)
        b.reportDamage(50);
        assertTrue(b.isUsable());
        assertEquals(50, b.getDamageLevel());

        // Add 40 → 90 (or capped at 100)
        b.reportDamage(40);
        assertTrue(b.getDamageLevel() >= 90);
        assertFalse(b.isUsable()); // Damage exceeds 80 → not usable

        // Negative damage input should not reduce total damage
        b.reportDamage(-10);
        assertTrue(b.getDamageLevel() >= 90);
    }
}
