package com.university;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Academic Department (e.g., Computer Science).
 * Manages budget, faculty list, and course offerings.
 */
public class Department {

    private String deptName;
    private String deptCode;
    private Faculty headOfDept;
    private List<Faculty> facultyList;
    private List<Course> courseList;
    private double budget;

    public Department(String deptName, String deptCode, double initialBudget) {
        if (deptName == null || deptName.isEmpty()) {
            throw new IllegalArgumentException("Department name required.");
        }
        if (initialBudget < 0) {
            throw new IllegalArgumentException("Budget cannot be negative.");
        }
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.budget = initialBudget;
        this.facultyList = new ArrayList<>();
        this.courseList = new ArrayList<>();
    }

    public void assignHead(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty cannot be null.");
        }
        if (!facultyList.contains(faculty)) {
            facultyList.add(faculty);
        }
        this.headOfDept = faculty;
    }

    public void addFaculty(Faculty faculty) {
        if (faculty != null && !facultyList.contains(faculty)) {
            facultyList.add(faculty);
        }
    }

    public void removeFaculty(Faculty faculty) {
        facultyList.remove(faculty);
    }

    public void addCourse(Course course) {
        if (course != null && !courseList.contains(course)) {
            courseList.add(course);
        }
    }

    /**
     * Calculates the total salary expense for the department.
     * Mutation Target: Changing += to -= or skipping elements.
     */
    public double calculateTotalSalaryExpense() {
        double total = 0.0;
        for (Faculty f : facultyList) {
            total += f.getSalary();
        }
        return total;
    }

    /**
     * Checks if the department can afford to hire a new professor.
     * @param salaryRequest The salary requested by the new hire.
     * @return true if budget permits.
     */
    public boolean canAffordHire(double salaryRequest) {
        double currentExpenses = calculateTotalSalaryExpense();
        // Mutation Target: Changing <= to <
        return (currentExpenses + salaryRequest) <= this.budget;
    }

    public void increaseBudget(double amount) {
        if (amount > 0) {
            this.budget += amount;
        }
    }

    public String getDeptName() { return deptName; }
    public String getDeptCode() { return deptCode; }
    public Faculty getHeadOfDept() { return headOfDept; }
    public List<Faculty> getFacultyList() { return facultyList; }
    public List<Course> getCourseList() { return courseList; }
    public double getBudget() { return budget; }
}