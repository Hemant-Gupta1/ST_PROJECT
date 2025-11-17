package com.university;

import org.junit.jupiter.api.Test;

// import src.main.java.com.university.LibraryBook;

import static org.junit.jupiter.api.Assertions.*;

class LibraryBookTest {

    @Test
    void testDamageBoundaries() {
        LibraryBook b = new LibraryBook("1", "T", "A");
        
        // Exact Boundary: 79 damage (Should be Usable)
        b.reportDamage(79);
        assertTrue(b.isUsable());

        // Exact Boundary: 1 more damage -> 80 (Should be Unusable)
        b.reportDamage(1); 
        assertEquals(80, b.getDamageLevel());
        assertFalse(b.isUsable()); 
    }

    @Test
    void testDamageCapAt100() {
        LibraryBook b = new LibraryBook("1", "T", "A");
        b.reportDamage(50);
        b.reportDamage(60); // Total 110
        
        // Logic caps it at 100
        assertEquals(100, b.getDamageLevel());
    }
    @Test
    void testBookCreation() {
        LibraryBook b = new LibraryBook("123", "Title", "Author");
        assertEquals("123", b.getIsbn());
        assertEquals("Title", b.getTitle());
        assertEquals("Author", b.getAuthor());
        assertEquals(0, b.getDamageLevel());
        assertFalse(b.isBorrowed());
    }

    @Test
    void testInvalidBook() {
        assertThrows(IllegalArgumentException.class, () -> new LibraryBook(null, "T", "A"));
        assertThrows(IllegalArgumentException.class, () -> new LibraryBook("1", null, "A"));
    }

    @Test
    void testBorrowReturnFlow() {
        LibraryBook b = new LibraryBook("1", "T", "A");
        
        b.borrow();
        assertTrue(b.isBorrowed());
        
        // Try borrowing again
        assertThrows(IllegalStateException.class, b::borrow);
        
        b.returnBook();
        assertFalse(b.isBorrowed());
    }

    @Test
    void testDamageLogic() {
        LibraryBook b = new LibraryBook("1", "T", "A");
        assertTrue(b.isUsable()); // 0 damage

        b.reportDamage(50);
        assertTrue(b.isUsable()); // 50 damage (Limit is 80)
        assertEquals(50, b.getDamageLevel());

        b.reportDamage(40); 
        // 50 + 40 = 90. Should be capped at 100 if logic exists, or just > 80
        assertTrue(b.getDamageLevel() >= 90);
        assertFalse(b.isUsable()); // > 80 damage implies not usable

        // Test negative damage input
        b.reportDamage(-10);
        assertTrue(b.getDamageLevel() >= 90); // Should not decrease
    }
}