package com.university;

/**
 * Handles higher-level enrollment and withdrawal logic.
 * Works like a controller/service layer that applies rules
 * before modifying Course or Student state.
 */
public class EnrollmentService {

    /**
     * Attempts to enroll a student in a course based on several rules:
     * 1. Student and Course must not be null
     * 2. Student must not be on probation
     * 3. Course must have available seats
     * 4. Student cannot exceed the 20-credit limit
     */
    public boolean enroll(Student student, Course course) {

        // Basic null checks
        if (student == null || course == null) {
            return false;
        }

        // Rule: Students on probation cannot enroll
        if (student.isProbation()) {
            System.out.println("Enrollment failed: Student is on probation.");
            return false;
        }

        // Rule: Course must not be full
        if (course.isFull()) {
            System.out.println("Enrollment failed: Course is full.");
            return false;
        }

        // Rule: Combined credits must not exceed limit (20)
        int projectedCredits = student.getTotalCredits() + course.getCredits();
        if (projectedCredits > 20) {
            System.out.println("Enrollment failed: Credit limit exceeded.");
            return false;
        }

        // Execute final enrollment operation
        boolean success = course.enroll(student);

        // In real systems: update billing, registration logs, etc.
        return success;
    }

    /**
     * Withdraws a student from a course.
     * If withdrawal happens past a deadline, outputs a penalty message.
     */
    public boolean withdraw(Student student, Course course, boolean isPastDeadline) {
        if (student == null || course == null) {
            return false;
        }

        // Remove student from course
        boolean removed = course.drop(student);

        // If dropped after deadline, simulate penalty recording
        if (removed && isPastDeadline) {
            System.out.println("Student withdrew with penalty.");
        }

        return removed;
    }



    /**
     * Allows a student to audit a course.
     * Auditing does not require credit checks but still needs available seats.
     */

    
    public void auditCourse(Student student, Course course) {
        if (course.getAvailableSeats() > 0) {
            System.out.println(
                    "Student " + student.getFullName() +
                            " is auditing " + course.getCourseName());
        }
    }
}
