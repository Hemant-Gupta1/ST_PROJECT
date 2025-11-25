package com.university;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that print statements inside EnrollmentService, Main, and other components
 * are executed correctly by capturing console output.
 * This ensures mutation testing kills mutants that remove, alter, or bypass println calls.
 */
class OutputCaptureTest {

    // Buffers to capture stdout and stderr
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    // Store original streams to restore after each test
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        // Redirect System.out and System.err so tests can inspect console output
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Restore actual console output for normal behavior
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // --- ENSURE PRINT MESSAGES IN ENROLLMENT SERVICE EXECUTE ---

    @Test
    void testEnrollmentProbationPrint() {
        // Setup: student is placed on probation
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        s.updateAcademicRecord(1.0, 10); // Forces probation
        Course c = new Course("C1", "N", 3, 10);

        // Attempt enrollment triggers print
        service.enroll(s, c);

        // Mutation protection: verify probation error message appears
        assertTrue(outContent.toString().contains("Enrollment failed: Student is on probation."));
    }

    @Test
    void testEnrollmentFullPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s1 = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Student s2 = new Student("S2", "C", "D", "e@e.com", 20, "M");
        Course c = new Course("C1", "N", 3, 1); // Only 1 seat

        // First student fills capacity
        service.enroll(s1, c);

        // Prepare fresh output buffer
        outContent.reset();

        // Second student attempts to enroll → should print "Course is full"
        service.enroll(s2, c);
        assertTrue(outContent.toString().contains("Enrollment failed: Course is full."));
    }

    @Test
    void testEnrollmentCreditLimitPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");

        // Student already has 18 credits
        s.updateAcademicRecord(4.0, 18);

        Course c = new Course("C1", "N", 3, 10);

        // Attempting to exceed the 20-credit limit triggers message
        service.enroll(s, c);

        assertTrue(outContent.toString().contains("Enrollment failed: Credit limit exceeded."));
    }

    @Test
    void testAuditPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Course c = new Course("C1", "N", 3, 10);

        // Auditing a course always prints confirmation
        service.auditCourse(s, c);

        assertTrue(outContent.toString().contains("is auditing"));
    }

    @Test
    void testWithdrawPenaltyPrint() {
        EnrollmentService service = new EnrollmentService();
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Course c = new Course("C1", "N", 3, 10);

        service.enroll(s, c);
        outContent.reset(); // Reset output before testing penalty message

        // Withdrawal beyond deadline prints penalty message
        service.withdraw(s, c, true);

        assertTrue(outContent.toString().contains("Student withdrew with penalty."));
    }

    // --- PRINT TESTING FOR MAIN EXECUTION ---

    @Test
    void testMainOutput() {
        // Execute full Main class for coverage and printed output verification
        Main.main(new String[]{});

        String output = outContent.toString();

        // 1. Check system start/end
        assertTrue(output.contains("=== University System Starting ==="), "Failed to find 'Starting' banner.");
        assertTrue(output.contains("=== System Finished ==="), "Failed to find 'Finished' banner.");
        
        // 2. Check the enrollment status - NOW CORRECTLY ASSERTING THE ADDED PRINT LINE
        assertTrue(output.contains("Enrolling Alice: SUCCESS"), "Failed to find SUCCESS enrollment."); 
        
        // 3. Check the final GPA calculation
        assertTrue(output.contains("Alice Wonder | GPA: 4.0"), "Failed to find Alice's final GPA.");
    }
}
