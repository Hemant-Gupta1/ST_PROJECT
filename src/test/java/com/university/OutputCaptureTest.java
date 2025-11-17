package com.university;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class OutputCaptureTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        // Redirect console output to our variable
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Restore console back to normal
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // --- KILL MUTANTS IN ENROLLMENT SERVICE (PRINT STATEMENTS) ---
    @Test
    void testEnrollmentProbationPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        s.updateAcademicRecord(1.0, 10); // Force Probation
        Course c = new Course("C1", "N", 3, 10);

        service.enroll(s, c);
        
        // If the tool deletes the println, this assertion will fail
        assertTrue(outContent.toString().contains("Enrollment failed: Student is on probation."));
    }

    @Test
    void testEnrollmentFullPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s1 = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Student s2 = new Student("S2", "C", "D", "e@e.com", 20, "M");
        Course c = new Course("C1", "N", 3, 1); // Capacity 1

        service.enroll(s1, c);
        outContent.reset(); // Clear previous output
        
        service.enroll(s2, c); // Should fail
        assertTrue(outContent.toString().contains("Enrollment failed: Course is full."));
    }
    
    @Test
    void testEnrollmentCreditLimitPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        s.updateAcademicRecord(4.0, 18); // 18 credits
        Course c = new Course("C1", "N", 3, 10); // +3 = 21 > 20
        
        service.enroll(s, c);
        assertTrue(outContent.toString().contains("Enrollment failed: Credit limit exceeded."));
    }

    @Test
    void testAuditPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Course c = new Course("C1", "N", 3, 10);

        service.auditCourse(s, c);
        assertTrue(outContent.toString().contains("is auditing"));
    }
    
    @Test
    void testWithdrawPenaltyPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Course c = new Course("C1", "N", 3, 10);
        
        service.enroll(s, c);
        outContent.reset();
        
        service.withdraw(s, c, true); // Late withdraw
        assertTrue(outContent.toString().contains("Student withdrew with penalty."));
    }

    // --- KILL MUTANTS IN VALIDATION UTILS (LOGGING) ---
    @Test
    void testValidationLogging() {
        ValidationUtils.logInfo("TestContext", "TestMessage");
        assertTrue(outContent.toString().contains("[INFO] TestContext: TestMessage"));

        ValidationUtils.logError("TestContext", "ErrorMessage");
        assertTrue(errContent.toString().contains("[ERROR] TestContext: ErrorMessage"));
    }

    // --- KILL MUTANTS IN MAIN (SYSTEM STARTUP) ---
    @Test
    void testMainOutput() {
        Main.main(new String[]{});
        String output = outContent.toString();
        
        // Verify Main is actually doing its job
        assertTrue(output.contains("=== University System Starting ==="));
        assertTrue(output.contains("Enrolling Alice: SUCCESS"));
        assertTrue(output.contains("=== System Finished ==="));
    }
}