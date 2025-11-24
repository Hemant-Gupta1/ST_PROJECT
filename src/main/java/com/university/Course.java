package com.university;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a University Course with details like ID, name, credits,
 * capacity, instructor, and enrolled students.
 */
public class Course {

    // Unique course identifier (e.g., CS101)
    private String courseId;

    // Full course name shown to students
    private String courseName;

    // Number of credits for the course (1–6)
    private int credits;

    // Maximum allowed students
    private int capacity;

    // Instructor assigned to teach this course
    private Faculty instructor;

    // List of currently enrolled students
    private List<Student> enrolledStudents;

    // Indicates whether course is active/open for enrollment
    private boolean isActive;

    /**
     * Creates a new Course after validating required fields.
     */
    public Course(String courseId, String courseName, int credits, int capacity) {
        // Basic validation
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
        this.isActive = true; // New courses are active by default
    }

    /**
     * Assigns a faculty member as instructor for this course.
     */
    public void setInstructor(Faculty instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null.");
        }
        this.instructor = instructor;
    }

    /**
     * Attempts to enroll a student in the course.
     * Returns false if course inactive, full, or student already enrolled.
     */
    public boolean enroll(Student student) {
        if (!isActive)
            return false;
        if (isFull())
            return false;
        if (enrolledStudents.contains(student))
            return false;

        enrolledStudents.add(student);
        return true;
    }

    /**
     * Removes a student from the course, if enrolled.
     */
    public boolean drop(Student student) {
        return enrolledStudents.remove(student);
    }

    /**
     * Checks if the course has no available seats.
     * Mutation testing target: >= vs >
     */
    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    /**
     * Returns how many seats are still available.
     */
    public int getAvailableSeats() {
        return capacity - enrolledStudents.size();
    }

    /**
     * Cancels the course:
     * - Marks it inactive
     * - Clears all enrollments
     */
    public void cancelCourse() {
        this.isActive = false;
        this.enrolledStudents.clear();
    }

    // ----- Getters -----

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public Faculty getInstructor() {
        return instructor;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public boolean isActive() {
        return isActive;
    }
}
