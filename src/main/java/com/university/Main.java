package com.university;

/**
 * Main entry point to demonstrate the application functionality.
 * This satisfies the requirement of providing "complete functionality" 
 * and allows you to see the system in action.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== University System Starting ===");

        // 1. Setup Department and Faculty
        Department csDept = new Department("Computer Science", "CS", 50000);
        Faculty profSmith = new Faculty("F001", "John", "Smith", "john@univ.edu", 45, "CS", 8000);
        csDept.assignHead(profSmith);
        csDept.addFaculty(profSmith);
        System.out.println("Department Created: " + csDept.getDeptName() + " Head: " + csDept.getHeadOfDept().getFullName());

        // 2. Create Course
        Course javaCourse = new Course("CS101", "Advanced Java", 4, 30);
        javaCourse.setInstructor(profSmith);
        csDept.addCourse(javaCourse);
        System.out.println("Course Created: " + javaCourse.getCourseName());

        // 3. Create Students
        Student s1 = new Student("S001", "Alice", "Wonder", "alice@univ.edu", 20, "CS");
        Student s2 = new Student("S002", "Bob", "Builder", "bob@univ.edu", 21, "CS");
        
        // 4. Enrollment Service Logic
        EnrollmentService service = new EnrollmentService();
        System.out.println("\n--- Attempting Enrollment ---");
        
        boolean success1 = service.enroll(s1, javaCourse);
        System.out.println("Enrolling Alice: " + (success1 ? "SUCCESS" : "FAILED"));
        
        boolean success2 = service.enroll(s2, javaCourse);
        System.out.println("Enrolling Bob: " + (success2 ? "SUCCESS" : "FAILED"));

        // 5. Grade Updates and Logic
        System.out.println("\n--- End of Semester ---");
        s1.updateAcademicRecord(4.0, 4); // Alice gets an A
        s2.updateAcademicRecord(2.5, 4); // Bob gets a C+

        System.out.println(s1.getFullName() + " GPA: " + s1.getGpa() + " (Deans List: " + s1.isDeansList() + ")");
        System.out.println(s2.getFullName() + " GPA: " + s2.getGpa() + " (Probation: " + s2.isProbation() + ")");
        
        System.out.println("=== System Finished ===");
    }
}