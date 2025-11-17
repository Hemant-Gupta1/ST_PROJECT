package com.university;

/**
 * Represents a physical book in the library.
 */
public class LibraryBook {

    private String isbn;
    private String title;
    private String author;
    private boolean isBorrowed;
    private int damageLevel; // 0 to 100

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

    public void borrow() {
        if (isBorrowed) {
            throw new IllegalStateException("Book is already borrowed.");
        }
        this.isBorrowed = true;
    }

    public void returnBook() {
        this.isBorrowed = false;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void reportDamage(int damage) {
        if (damage < 0) damage = 0;
        this.damageLevel += damage;
        if (this.damageLevel > 100) {
            this.damageLevel = 100;
        }
    }

    public boolean isUsable() {
        // Mutation Target: > 80 condition
        return damageLevel < 80;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getDamageLevel() { return damageLevel; }
}