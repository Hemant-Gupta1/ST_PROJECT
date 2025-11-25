package com.university;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic department (e.g., CSE, ECE).
 * Maintains budget, faculty list, courses, and department head.
 */
public class Department {

    // Full department name (e.g., "Computer Science")
    private String deptName;

    // Short department code (e.g., "CSE")
    private String deptCode;

    // Faculty member assigned as Head of Department
    private Faculty headOfDept;

    // List of all faculty belonging to this department
    private List<Faculty> facultyList;

    // List of all courses offered by the department
    private List<Course> courseList;

    // Total allocated budget for salaries and expenses
    private double budget;

    /**
     * Creates a Department after validating basic inputs.
     */
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

    /**
     * Assigns a faculty member as Head of Department.
     * Automatically adds them to faculty list if not already present.
     */
    public void assignHead(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty cannot be null.");
        }
        if (!facultyList.contains(faculty)) {
            facultyList.add(faculty);
        }
        this.headOfDept = faculty;
    }

    /**
     * Adds a faculty member to the department.
     */
    public void addFaculty(Faculty faculty) {
        if (faculty != null && !facultyList.contains(faculty)) {
            facultyList.add(faculty);
        }
    }

    /**
     * Removes a faculty member from the department.
     */
    public void removeFaculty(Faculty faculty) {
        facultyList.remove(faculty);
    }

    /**
     * Adds a course to the department's course offerings.
     */
    public void addCourse(Course course) {
        if (course != null && !courseList.contains(course)) {
            courseList.add(course);
        }
    }

    /**
     * Computes the total salary cost of all faculty.
     * Mutation Target: += vs -= or incorrect loop logic.
     */
    public double calculateTotalSalaryExpense() {
        double total = 0.0;
        for (Faculty f : facultyList) {
            total += f.getSalary();
        }
        return total;
    }

    /**
     * Determines whether the department has enough budget
     * to hire a new faculty member at the requested salary.
     *
     * Mutation Target: <= vs < to affect hiring boundary condition.
     */
    public boolean canAffordHire(double salaryRequest) {
        double currentExpenses = calculateTotalSalaryExpense();
        return (currentExpenses + salaryRequest) <= this.budget;
    }

    /**
     * Increases department budget by a given positive amount.
     */
    public void increaseBudget(double amount) {
        if (amount > 0) {
            this.budget += amount;
        }
    }

    // ----- Getters -----

    
    public String getDeptName() {
        return deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }


    public Faculty getHeadOfDept() {
        return headOfDept;
    }

    public List<Faculty> getFacultyList() {
        return facultyList;
    }


    public List<Course> getCourseList() {
        return courseList;
    }


    public double getBudget() {
        return budget;
    }
}
