package com.university;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages borrowing and returning of books.
 * Calculates fines for overdue books.
 */
public class LibrarySystem {

    private static final double DAILY_FINE_RATE = 0.50; // 50 cents per day
    private static final int MAX_BORROW_DAYS = 14;
    
    private Map<String, LibraryBook> catalog;
    private Map<String, Integer> borrowedDateMap; // ISBN -> Day of Year

    public LibrarySystem() {
        this.catalog = new HashMap<>();
        this.borrowedDateMap = new HashMap<>();
    }

    public void addBook(LibraryBook book) {
        if (book != null) {
            catalog.put(book.getIsbn(), book);
        }
    }

    public boolean checkoutBook(Student student, String isbn, int currentDayOfYear) {
        if (!catalog.containsKey(isbn)) {
            return false;
        }
        
        LibraryBook book = catalog.get(isbn);
        
        // Logic: Cannot borrow if already borrowed or too damaged
        if (book.isBorrowed() || !book.isUsable()) {
            return false;
        }

        // Logic: Students with probation cannot borrow
        if (student.isProbation()) {
            return false;
        }

        book.borrow();
        borrowedDateMap.put(isbn, currentDayOfYear);
        return true;
    }

    /**
     * Returns a book and calculates fine if overdue.
     * @return The amount of fine due (0.0 if on time)
     */
    public double returnBook(String isbn, int currentDayOfYear) {
        if (!catalog.containsKey(isbn) || !borrowedDateMap.containsKey(isbn)) {
            throw new IllegalArgumentException("Invalid return transaction.");
        }

        LibraryBook book = catalog.get(isbn);
        int borrowDay = borrowedDateMap.get(isbn);
        
        book.returnBook();
        borrowedDateMap.remove(isbn);

        int daysKept = currentDayOfYear - borrowDay;
        
        // Handle year wrapping (simplified logic for mutation testing)
        if (daysKept < 0) { 
            daysKept += 365; 
        }

        // Mutation Target: Calculation of fine
        if (daysKept > MAX_BORROW_DAYS) {
            int overdueDays = daysKept - MAX_BORROW_DAYS;
            return overdueDays * DAILY_FINE_RATE;
        }

        return 0.0;
    }

    public int getCatalogSize() {
        return catalog.size();
    }
    
    public boolean isBookAvailable(String isbn) {
        return catalog.containsKey(isbn) && !catalog.get(isbn).isBorrowed();
    }
}