package com.university;

/**
 * Represents a Faculty member in the university.
 * Inherits basic identity details from Person.
 * Adds salary, department, service years, and tenure logic.
 */
public class Faculty extends Person {

    // Academic department the faculty belongs to
    private String department;

    // Current salary of the faculty member
    private double salary;

    // Number of years worked in the university
    private int yearsOfService;

    // Indicates if the faculty member has earned tenure
    private boolean isTenured;

    /**
     * Creates a Faculty object with basic details.
     * Validates department name and base salary.
     */
    public Faculty(String id, String firstName, String lastName, String email, int age,
            String department, double baseSalary) {
        super(id, firstName, lastName, email, age);

        validateDepartment(department);
        validateSalary(baseSalary);

        this.department = department;
        this.salary = baseSalary;
        this.yearsOfService = 0; // Starts with 0 years
        this.isTenured = false; // Tenure initially false
    }

    /**
     * Ensures department name is valid.
     */
    private void validateDepartment(String dept) {
        if (dept == null || dept.length() < 2) {
            throw new IllegalArgumentException("Department name too short.");
        }
    }

    /**
     * Ensures salary is non-negative.
     */
    private void validateSalary(double sal) {
        if (sal < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
    }

    /**
     * Increases service years by 1 and checks if tenure should be granted.
     */
    public void incrementServiceYears() {
        this.yearsOfService++;
        checkTenureEligibility();
    }

    /**
     * Grants tenure automatically if service > 5 years.
     * Mutation Target: Changing > to >= or a different threshold.
     */
    private void checkTenureEligibility() {
        if (this.yearsOfService > 5) {
            this.isTenured = true;
        }
    }


    
    /**
     * Gives a raise based on percentage increase.
     * Mutation Target: raise calculation logic.
     */
    public void giveRaise(double percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("Raise percentage cannot be negative.");
        }
        double raiseAmount = this.salary * (percentage / 100.0);
        this.salary += raiseAmount;
    }

    // ----- Getters -----


    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }

    public boolean isTenured() {
        return isTenured;
    }
}
