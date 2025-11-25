package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests accumulation-based state changes in multiple classes:
 * - Student credit accumulation
 * - Faculty service years
 * - Course enrollment count
 * - Library book damage accumulation
 * - Department faculty removal
 */
class StateAccumulationTest {

    // --- TARGET: STUDENT CREDIT ACCUMULATION ---
    @Test
    void testStudentCreditAccumulation() {
        Student s = new Student("S1", "A", "B", "e@e.com", 20, "M");

        // First update: add 3 credits
        s.updateAcademicRecord(4.0, 3);
        assertEquals(3, s.getTotalCredits());

        // Second update: add another 3 credits → total should be 6
        // Mutant would incorrectly replace credits instead of accumulating
        s.updateAcademicRecord(4.0, 3);
        assertEquals(6, s.getTotalCredits(), "Credits should accumulate, not replace");
    }

    // --- TARGET: FACULTY YEAR INCREMENT ---
    @Test
    void testFacultyYearAccumulation() {
        Faculty f = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);

        // Increment multiple times; ensures yearsOfService accumulates
        f.incrementServiceYears(); // 1
        f.incrementServiceYears(); // 2
        f.incrementServiceYears(); // 3

        assertEquals(3, f.getYearsOfService(), "Years should count up correctly");
    }

    // --- TARGET: COURSE DECREMENT ---
    @Test
    void testCourseEnrollmentDecrement() {
        Course c = new Course("C1", "N", 3, 10);
        Student s1 = new Student("S1", "A", "B", "e@e.com", 20, "M");
        Student s2 = new Student("S2", "C", "D", "e@e.com", 20, "M");

        // Two enrollments → size should be 2
        c.enroll(s1);
        c.enroll(s2);
        assertEquals(2, c.getEnrolledStudents().size());

        // Dropping decreases count
        c.drop(s1);
        assertEquals(1, c.getEnrolledStudents().size(), "Count should decrease on drop");

        // Dropping last student → count goes to 0
        c.drop(s2);
        assertEquals(0, c.getEnrolledStudents().size());
    }

    // --- TARGET: LIBRARY DAMAGE ACCUMULATION ---
    @Test
    void testLibraryBookDamageAccumulation() {
        LibraryBook b = new LibraryBook("1", "T", "A");

        // First damage report
        b.reportDamage(10);
        assertEquals(10, b.getDamageLevel());

        // Additional damage should accumulate
        b.reportDamage(20);
        assertEquals(30, b.getDamageLevel(), "Damage should accumulate");
    }

    // --- TARGET: DEPARTMENT REMOVAL ---
    @Test
    void testDepartmentRemovalLogic() {
        Department d = new Department("D", "C", 1000);
        Faculty f = new Faculty("1", "A", "B", "e@e.com", 30, "Dept", 1000);

        // Add then remove faculty member
        d.addFaculty(f);
        assertEquals(1, d.getFacultyList().size());

        d.removeFaculty(f);
        assertEquals(0, d.getFacultyList().size(), "Faculty should be removed from list");
    }
}
