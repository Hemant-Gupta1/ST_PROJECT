package com.university;

import org.junit.jupiter.api.Test;

// import src.main.java.com.university.Department;
// import src.main.java.com.university.Faculty;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Department class focusing on budget boundaries,
 * salary calculations, budget increase logic, and getter behavior.
 */
class DepartmentTest {

    @Test
    void testExactBudgetLimit() {
        // Department with initial budget 3000
        Department dept = new Department("Art", "AR", 3000);

        // Add faculty with 2000 salary → remaining budget = 1000
        Faculty f1 = new Faculty("F1", "A", "B", "a@b.com", 30, "AR", 2000);
        dept.addFaculty(f1);

        // Hiring cost exactly equal to remaining budget → should be allowed
        assertTrue(dept.canAffordHire(1000));

        // Hiring cost exceeding budget → should be denied
        assertFalse(dept.canAffordHire(1001));
    }

    @Test
    void testBudgetCalculation() {
        Department dept = new Department("Computer Science", "CS", 5000);

        // Two faculty salaries: 2000 + 2000 = 4000
        Faculty f1 = new Faculty("F1", "A", "B", "a@b.com", 30, "CS", 2000);
        Faculty f2 = new Faculty("F2", "C", "D", "c@d.com", 35, "CS", 2000);

        dept.addFaculty(f1);
        dept.addFaculty(f2);

        // Salary sum should be 4000
        assertEquals(4000.0, dept.calculateTotalSalaryExpense());

        // Remaining budget = 5000 - 4000 = 1000 → affordable
        assertTrue(dept.canAffordHire(1000));

        // Exceeds remaining budget → not affordable
        assertFalse(dept.canAffordHire(1001));
    }

    @Test
    void testBudgetIncreases() {
        Department dept = new Department("Math", "MA", 1000);

        // Valid budget increase
        dept.increaseBudget(500);
        assertEquals(1500.0, dept.getBudget(), 0.01);

        // Negative increase ignored by logic (since amount > 0 check)
        dept.increaseBudget(-100);
        assertEquals(1500.0, dept.getBudget(), 0.01);
    }

    @Test
    void testDepartmentGetters() {
        // Ensures getters return correct values for mutation coverage
        Department dept = new Department("History", "HIS", 500);

        assertEquals("History", dept.getDeptName());
        assertEquals("HIS", dept.getDeptCode());

        // No head assigned yet
        assertNull(dept.getHeadOfDept());
    }
}
