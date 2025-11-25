package com.university;

/**
 * Entry point for demonstrating the University Management System.
 * This version uses ONLY the 8 core classes currently present in src/main/java.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("=== University System Starting ===");
        
        // --- 1. Setup Core Entities ---
        Department csDept = new Department("Computer Science", "CS", 50000);
        Faculty profSmith = new Faculty("F001", "John", "Smith", "john@univ.edu", 45, "CS", 8000);
        Course javaCourse = new Course("CS101", "Advanced Java", 4, 30);
        Student s1 = new Student("S001", "Alice", "Wonder", "alice@univ.edu", 20, "CS");
        Student s2 = new Student("S002", "Bob", "Builder", "bob@univ.edu", 21, "CS");
        EnrollmentService service = new EnrollmentService();

        // Assign HOD and register faculty
        csDept.assignHead(profSmith);
        csDept.addFaculty(profSmith);
        javaCourse.setInstructor(profSmith);
        csDept.addCourse(javaCourse);

        // Demonstrates Department, Faculty, Course usage
        System.out.println("Department Head Assigned: " + csDept.getHeadOfDept().getFullName());
        System.out.println("Course Instructor: " + javaCourse.getInstructor().getFullName());
        
        // --- 2. Enrollment & Grading ---
        System.out.println("\n--- Enrollment ---");
        
        boolean success1 = service.enroll(s1, javaCourse);
        System.out.println("Enrolling Alice: " + (success1 ? "SUCCESS" : "FAILED")); // <--- FIX: Prints result
        
        boolean success2 = service.enroll(s2, javaCourse);
        System.out.println("Enrolling Bob: " + (success2 ? "SUCCESS" : "FAILED"));   // <--- FIX: Prints result
        
        System.out.println("Total Enrolled in Java: " + javaCourse.getEnrolledStudents().size());

        // Simulate End-of-Semester
        s1.updateAcademicRecord(4.0, 4); 
        s2.updateAcademicRecord(2.5, 4);
        
        // --- 3. Library System Demonstration (Uses LibraryBook, LibrarySystem) ---
        LibrarySystem library = new LibrarySystem();
        LibraryBook book = new LibraryBook("978-0134685991", "Java Testing Guide", "Offutt");
        library.addBook(book);

        // Simulate fine calculation (borrowing 16 days after Day 1)
        library.checkoutBook(s1, book.getIsbn(), 1); 
        double fine = library.returnBook(book.getIsbn(), 17); // Overdue fine check

        System.out.println("\n--- Library System ---");
        System.out.println("Book Status: " + book.getTitle() + " available? " + !book.isBorrowed());
        System.out.println("Fine Due for Alice: $" + fine);

        // --- 4. Final Status (Uses Student, Person) ---
        System.out.println("\n--- Final Academic Standing ---");
        System.out.println(s1.getFullName() + " | GPA: " + s1.getGpa() + " | Dean's List: " + s1.isDeansList());
        System.out.println(s2.getFullName() + " | GPA: " + s2.getGpa() + " | Probation: " + s2.isProbation());
        System.out.println("=== System Finished ===");
    }
}