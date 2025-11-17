package com.university;

/**
 * Abstract base class representing a person within the university system.
 * This class encapsulates shared data between Students, Faculty, and Staff.
 * * Logic here provides mutation testing opportunities regarding age verification
 * and email validation.
 */
public abstract class Person {

    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected int age;
    protected String address;

    /**
     * Constructor for Person.
     * * @param id Unique Identifier
     * @param firstName First Name
     * @param lastName Last Name
     * @param email Email Address
     * @param age Age in years
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
        this.address = "Unknown"; // Default value
    }

    private void validateInput(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    private void validateAge(int age) {
        // Mutation testing target: changing 16 to 15 or 17
        if (age < 16) {
            throw new IllegalArgumentException("Person must be at least 16 years old.");
        }
    }

    public void setAddress(String address) {
        if (address != null && !address.isEmpty()) {
            this.address = address;
        }
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    // Getters and Setters for encapsulation
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
}