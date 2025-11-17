package com.university;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a University Course.
 * Manages enrollment capacity and assigned faculty.
 */
public class Course {

    private String courseId;
    private String courseName;
    private int credits;
    private int capacity;
    private Faculty instructor;
    private List<Student> enrolledStudents;
    private boolean isActive;

    public Course(String courseId, String courseName, int credits, int capacity) {
        if (courseId == null || courseId.isEmpty()) {
            throw new IllegalArgumentException("Course ID is required.");
        }
        if (credits < 1 || credits > 6) {
            throw new IllegalArgumentException("Credits must be between 1 and 6.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }

        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>();
        this.isActive = true;
    }

    public void setInstructor(Faculty instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null.");
        }
        this.instructor = instructor;
    }

    public boolean enroll(Student student) {
        if (!isActive) return false;
        if (isFull()) return false;
        if (enrolledStudents.contains(student)) return false;

        enrolledStudents.add(student);
        return true;
    }

    public boolean drop(Student student) {
        return enrolledStudents.remove(student);
    }

    public boolean isFull() {
        // Mutation Target: >= vs >
        return enrolledStudents.size() >= capacity;
    }

    public int getAvailableSeats() {
        return capacity - enrolledStudents.size();
    }

    public void cancelCourse() {
        this.isActive = false;
        this.enrolledStudents.clear();
    }

    // Standard Getters
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public int getCapacity() { return capacity; }
    public Faculty getInstructor() { return instructor; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public boolean isActive() { return isActive; }
}