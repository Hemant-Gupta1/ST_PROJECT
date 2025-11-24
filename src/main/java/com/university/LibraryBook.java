package com.university;

/**
 * Represents a physical book in the library system.
 * Tracks borrow status and damage level.
 */
public class LibraryBook {

    // Unique ISBN identifier for the book
    private String isbn;

    // Title of the book
    private String title;

    // Author of the book
    private String author;

    // Indicates if the book is currently borrowed
    private boolean isBorrowed;

    // Physical damage level (0 = perfect, 100 = unusable)
    private int damageLevel;

    /**
     * Creates a new library book with basic metadata.
     * ISBN and title are required fields.
     */
    public LibraryBook(String isbn, String title, String author) {
        if (isbn == null || title == null) {
            throw new IllegalArgumentException("ISBN and Title are required.");
        }

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
        this.damageLevel = 0;
    }

    /**
     * Marks the book as borrowed.
     * Throws exception if already borrowed.
     */
    public void borrow() {
        if (isBorrowed) {
            throw new IllegalStateException("Book is already borrowed.");
        }
        this.isBorrowed = true;
    }

    /**
     * Marks the book as returned.
     */
    public void returnBook() {
        this.isBorrowed = false;
    }

    /**
     * Returns whether the book is currently borrowed.
     */
    public boolean isBorrowed() {
        return isBorrowed;
    }

    /**
     * Reports damage to the book.
     * Damage accumulates and is capped at 100.
     */
    public void reportDamage(int damage) {
        if (damage < 0) {
            damage = 0; // Prevent negative damage
        }
        this.damageLevel += damage;

        if (this.damageLevel > 100) {
            this.damageLevel = 100; // Cap damage level
        }
    }

    /**
     * Determines whether the book is still usable.
     * Mutation Target: damageLevel < 80 condition.
     */
    public boolean isUsable() {
        return damageLevel < 80;
    }

    // ----- Getters -----

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getDamageLevel() {
        return damageLevel;
    }
}
