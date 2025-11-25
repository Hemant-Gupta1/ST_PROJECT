package com.university;

/**
 * Represents a Student in the university.
 * Extends Person and adds GPA, credit tracking, major, and academic status
 * logic.
 */
public class Student extends Person {

    // Current GPA of the student (0.0–4.0 scale)
    private double gpa;

    // Total number of credits earned so far
    private int totalCredits;

    // Primary field of study
    private String major;

    // Indicates if the student is an international student
    private boolean isInternational;

    /**
     * Creates a Student with required personal and academic details.
     */
    public Student(String id, String firstName, String lastName,
            String email, int age, String major) {
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
     * Updates GPA using weighted average based on new course grade.
     * Mutation Target: grade & credit math.
     *
     * @param grade   Grade received (0.0–4.0)
     * @param credits Course credits (must be positive)
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
     * Checks if the student qualifies for the Dean’s List.
     * Mutation Target: GPA threshold (3.5) and credit threshold (12).
     */
    
    public boolean isDeansList() {
        return this.gpa >= 3.5 && this.totalCredits >= 12;
    }

    /**
     * Determines if the student is on academic probation.
     * Mutation Target: GPA threshold (2.0).
     */
    public boolean isProbation() {
        return this.gpa < 2.0 && this.totalCredits > 0;
    }

    /**
     * Sets whether the student is international.
     */
    public void setInternational(boolean isInternational) {
        this.isInternational = isInternational;
    }

    public boolean isInternational() {
        return isInternational;
    }

    // ----- Getters -----

    public double getGpa() {
        return gpa;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public String getMajor() {
        return major;
    }

    /**
     * User-friendly string representation.
     */
    @Override
    public String toString() {
        return "Student: " + getFullName() + " [" + major + "]";
    }
}
