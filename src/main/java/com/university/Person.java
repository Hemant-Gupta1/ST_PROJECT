package com.university;

/**
 * Abstract base class for all people in the university system.
 * Shared fields and validation logic for Students, Faculty, and Staff.
 * Includes mutation targets in age and email validation.
 */
public abstract class Person {

    // Common identity attributes
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected int age;

    // Optional contact information
    protected String address;

    /**
     * Constructs a Person with common attributes.
     * Performs basic validation for ID, name, email, and age.
     */
    public Person(String id, String firstName, String lastName, String email, int age) {
        validateInput(id, "ID");
        validateInput(firstName, "First Name");
        validateInput(lastName, "Last Name");
        validateEmail(email);
        validateAge(age);

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;

        // Default address
        this.address = "Unknown";
    }

    /**
     * Ensures a field is not null or empty.
     */
    private void validateInput(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
    }

    /**
     * Validates basic email format.
     * Mutation target: relaxing or tightening validation conditions.
     */
    private void validateEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }



    /**
     * Ensures the age meets the minimum requirement.
     * Mutation target: threshold 16 → 15 or 17.
     */

    private void validateAge(int age) {
        if (age < 16) {
            throw new IllegalArgumentException("Person must be at least 16 years old.");
        }
    }

    
    /**
     * Updates address if valid.
     */
    public void setAddress(String address) {
        if (address != null && !address.isEmpty()) {
            this.address = address;
        }
    }

    /**
     * Returns the full name (First + Last).
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    // ----- Getters -----

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }
}
