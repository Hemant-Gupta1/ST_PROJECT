package com.university;

/**
 * Entry point for demonstrating the University Management System.
 * Shows how departments, faculty, courses, students, and services work
 * together.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("=== University System Starting ===");

        // ----- 1. Create Department and Faculty -----
        Department csDept = new Department("Computer Science", "CS", 50000);
        Faculty profSmith = new Faculty("F001", "John", "Smith", "john@univ.edu", 45, "CS", 8000);

        // Assign HOD and register faculty
        csDept.assignHead(profSmith);
        csDept.addFaculty(profSmith);

        System.out.println(
                "Department Created: " +
                        csDept.getDeptName() +
                        " | Head: " + csDept.getHeadOfDept().getFullName());

        // ----- 2. Create Course -----
        Course javaCourse = new Course("CS101", "Advanced Java", 4, 30);
        javaCourse.setInstructor(profSmith); // Assign faculty as instructor
        csDept.addCourse(javaCourse);

        System.out.println("Course Created: " + javaCourse.getCourseName());

        // ----- 3. Create Students -----
        Student s1 = new Student("S001", "Alice", "Wonder", "alice@univ.edu", 20, "CS");
        Student s2 = new Student("S002", "Bob", "Builder", "bob@univ.edu", 21, "CS");

        // ----- 4. Enrollment Process -----
        EnrollmentService service = new EnrollmentService();
        System.out.println("\n--- Attempting Enrollment ---");

        boolean success1 = service.enroll(s1, javaCourse);
        System.out.println("Enrolling Alice: " + (success1 ? "SUCCESS" : "FAILED"));

        boolean success2 = service.enroll(s2, javaCourse);
        System.out.println("Enrolling Bob: " + (success2 ? "SUCCESS" : "FAILED"));

        // ----- 5. Simulate End-of-Semester Results -----
        System.out.println("\n--- End of Semester ---");

        // Students update academic record with earned grade points
        s1.updateAcademicRecord(4.0, 4); // Alice earns an A (4 credits)
        s2.updateAcademicRecord(2.5, 4); // Bob earns a C+

        // Print GPA and academic standing
        System.out.println(
                s1.getFullName() +
                        " | GPA: " + s1.getGpa() +
                        " | Dean's List: " + s1.isDeansList());

        System.out.println(
                s2.getFullName() +
                        " | GPA: " + s2.getGpa() +
                        " | Probation: " + s2.isProbation());

        System.out.println("=== System Finished ===");
    }
}
