package com.university;

/**
 * Service class to handle complex enrollment logic.
 * This acts as the "Controller" logic for the application.
 */
public class EnrollmentService {

    /**
     * Enrolls a student in a course if all criteria are met.
     * Criteria:
     * 1. Student not null
     * 2. Course not null
     * 3. Student not on probation
     * 4. Course not full
     * 5. Student has enough remaining credits (Max 20)
     */
    public boolean enroll(Student student, Course course) {
        if (student == null || course == null) {
            return false;
        }

        // Rule: Students on probation cannot take new courses
        if (student.isProbation()) {
            System.out.println("Enrollment failed: Student is on probation.");
            return false; 
        }

        // Rule: Course Capacity
        if (course.isFull()) {
             System.out.println("Enrollment failed: Course is full.");
            return false;
        }

        // Rule: Credit Limit (Hardcoded to 20 for logic testing)
        int projectedCredits = student.getTotalCredits() + course.getCredits();
        if (projectedCredits > 20) {
             System.out.println("Enrollment failed: Credit limit exceeded.");
            return false;
        }

        // If all pass, execute state changes
        boolean success = course.enroll(student);
        if (success) {
            // In a real app we might calculate fees here
            return true;
        }
        return false;
    }

    /**
     * Withdraws a student from a course.
     * If the withdrawal happens after a certain deadline, there might be penalties
     * (represented here by boolean logic).
     */
    public boolean withdraw(Student student, Course course, boolean isPastDeadline) {
        if (student == null || course == null) {
            return false;
        }

        boolean removed = course.drop(student);
        
        if (removed && isPastDeadline) {
            // Logic: If past deadline, perhaps record a 'W' grade (simulated here)
            System.out.println("Student withdrew with penalty.");
        }
        
        return removed;
    }
    
    public void auditCourse(Student student, Course course) {
        // Simplified logic: Auditing doesn't check credits but checks capacity
        if (course.getAvailableSeats() > 0) {
            System.out.println("Student " + student.getFullName() + " is auditing " + course.getCourseName());
        }
    }
}