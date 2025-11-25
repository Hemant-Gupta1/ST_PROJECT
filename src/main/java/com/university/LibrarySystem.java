package com.university;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles library operations such as borrowing, returning,
 * tracking overdue days, and calculating fines.
 */
public class LibrarySystem {

    // Fine rate per overdue day
    private static final double DAILY_FINE_RATE = 0.50;

    // Maximum number of days a book can be borrowed
    private static final int MAX_BORROW_DAYS = 14;

    // Library catalog: ISBN -> Book object
    private Map<String, LibraryBook> catalog;

    // Tracks borrow date: ISBN -> Day of year when borrowed
    private Map<String, Integer> borrowedDateMap;

    /**
     * Initializes an empty library system.
     */
    public LibrarySystem() {
        this.catalog = new HashMap<>();
        this.borrowedDateMap = new HashMap<>();
    }

    /**
     * Adds a book to the library catalog.
     */
    public void addBook(LibraryBook book) {
        if (book != null) {
            catalog.put(book.getIsbn(), book);
        }
    }

    /**
     * Attempts to check out a book for a student.
     * Rules:
     * - Book must exist
     * - Book must not already be borrowed
     * - Book must be usable (damage < 80)
     * - Student must not be on probation
     */
    public boolean checkoutBook(Student student, String isbn, int currentDayOfYear) {
        if (!catalog.containsKey(isbn)) {
            return false;
        }

        LibraryBook book = catalog.get(isbn);

        // Cannot borrow if already borrowed or too damaged
        if (book.isBorrowed() || !book.isUsable()) {
            return false;
        }

        // Probation students cannot borrow
        if (student.isProbation()) {
            return false;
        }

        // Borrow the book and record the borrow date
        book.borrow();
        borrowedDateMap.put(isbn, currentDayOfYear);
        return true;
    }



    /**
     * Returns a borrowed book and calculates overdue fine if any.
     * Mutation Target: Fine calculation logic.
     *
     * @return total fine (0 if returned on time)
     */
    
    public double returnBook(String isbn, int currentDayOfYear) {
        if (!catalog.containsKey(isbn) || !borrowedDateMap.containsKey(isbn)) {
            throw new IllegalArgumentException("Invalid return transaction.");
        }

        LibraryBook book = catalog.get(isbn);
        int borrowDay = borrowedDateMap.get(isbn);

        // Mark book returned
        book.returnBook();
        borrowedDateMap.remove(isbn);

        int daysKept = currentDayOfYear - borrowDay;

        // Handle negative difference due to year wrap
        if (daysKept < 0) {
            daysKept += 365;
        }

        // Check if overdue
        if (daysKept > MAX_BORROW_DAYS) {
            int overdueDays = daysKept - MAX_BORROW_DAYS;
            return overdueDays * DAILY_FINE_RATE;
        }

        return 0.0;
    }

    /**
     * Returns the number of books in the catalog.
     */
    public int getCatalogSize() {
        return catalog.size();
    }

    /**
     * Checks if a book exists and is not currently borrowed.
     */
    public boolean isBookAvailable(String isbn) {
        return catalog.containsKey(isbn) && !catalog.get(isbn).isBorrowed();
    }
}
