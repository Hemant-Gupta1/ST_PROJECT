package com.university;

/**
 * Represents a Faculty member. Extends the Person class.
 * Contains logic for salary calculation and tenure tracking.
 */
public class Faculty extends Person {

    private String department;
    private double salary;
    private int yearsOfService;
    private boolean isTenured;

    public Faculty(String id, String firstName, String lastName, String email, int age, String department, double baseSalary) {
        super(id, firstName, lastName, email, age);
        validateDepartment(department);
        validateSalary(baseSalary);
        
        this.department = department;
        this.salary = baseSalary;
        this.yearsOfService = 0;
        this.isTenured = false;
    }

    private void validateDepartment(String dept) {
        if (dept == null || dept.length() < 2) {
            throw new IllegalArgumentException("Department name too short.");
        }
    }

    private void validateSalary(double sal) {
        if (sal < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
    }

    public void incrementServiceYears() {
        this.yearsOfService++;
        checkTenureEligibility();
    }

    /**
     * Logic to automatically grant tenure.
     * Mutation Target: > 5 years condition.
     */
    private void checkTenureEligibility() {
        if (this.yearsOfService > 5) {
            this.isTenured = true;
        }
    }

    public void giveRaise(double percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("Raise percentage cannot be negative.");
        }
        // Mutation Target: Calculation logic
        double raiseAmount = this.salary * (percentage / 100.0);
        this.salary += raiseAmount;
    }

    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public int getYearsOfService() { return yearsOfService; }
    public boolean isTenured() { return isTenured; }
}