package com.university;

/**
 * Represents a Student. Extends the Person class.
 * Contains logic for GPA calculation, credit tracking, and honor status.
 */
public class Student extends Person {

    private double gpa;
    private int totalCredits;
    private String major;
    private boolean isInternational;

    public Student(String id, String firstName, String lastName, String email, int age, String major) {
        super(id, firstName, lastName, email, age);
        if (major == null || major.isEmpty()) {
            throw new IllegalArgumentException("Major cannot be empty.");
        }
        this.major = major;
        this.gpa = 0.0;
        this.totalCredits = 0;
        this.isInternational = false;
    }

    /**
     * Updates the student's GPA based on a new course grade.
     * * @param grade The numerical grade received (0.0 to 4.0)
     * @param credits The number of credits for the course
     */
    public void updateAcademicRecord(double grade, int credits) {
        if (grade < 0.0 || grade > 4.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 4.0");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive.");
        }

        double currentPoints = this.gpa * this.totalCredits;
        double newPoints = grade * credits;
        
        this.totalCredits += credits;
        this.gpa = (currentPoints + newPoints) / this.totalCredits;
    }

    /**
     * Determines if the student is eligible for the Dean's List.
     * Mutation Target: The threshold 3.5 and credit requirement 12.
     */
    public boolean isDeansList() {
        return this.gpa >= 3.5 && this.totalCredits >= 12;
    }

    /**
     * Determines if the student is on academic probation.
     * Mutation Target: The threshold 2.0.
     */
    public boolean isProbation() {
        return this.gpa < 2.0 && this.totalCredits > 0;
    }

    public void setInternational(boolean isInternational) {
        this.isInternational = isInternational;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public double getGpa() { return gpa; }
    public int getTotalCredits() { return totalCredits; }
    public String getMajor() { return major; }
    
    @Override
    public String toString() {
        return "Student: " + getFullName() + " [" + major + "]";
    }
}