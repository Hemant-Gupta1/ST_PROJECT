package com.university;

import org.junit.jupiter.api.Test;

// import src.main.java.com.university.Department;
// import src.main.java.com.university.Faculty;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void testExactBudgetLimit() {
        Department dept = new Department("Art", "AR", 3000);
        // Hire F1 for 2000. Remaining = 1000.
        Faculty f1 = new Faculty("F1", "A", "B", "a@b.com", 30, "AR", 2000);
        dept.addFaculty(f1);
        
        // Try hiring F2 for exactly 1000. (Total 3000 == 3000). Should pass.
        assertTrue(dept.canAffordHire(1000));
        
        // Try hiring F3 for 1001. Should fail.
        assertFalse(dept.canAffordHire(1001));
    }
    @Test
    void testBudgetCalculation() {
        Department dept = new Department("Computer Science", "CS", 5000);
        Faculty f1 = new Faculty("F1", "A", "B", "a@b.com", 30, "CS", 2000);
        Faculty f2 = new Faculty("F2", "C", "D", "c@d.com", 35, "CS", 2000);

        dept.addFaculty(f1);
        dept.addFaculty(f2);

        assertEquals(4000.0, dept.calculateTotalSalaryExpense());
        
        // Budget is 5000, Expense is 4000. Remaining = 1000.
        // Can we hire someone for 1000? Yes.
        assertTrue(dept.canAffordHire(1000));
        
        // Can we hire someone for 1001? No.
        assertFalse(dept.canAffordHire(1001));
    }
    @Test
    void testBudgetIncreases() {
        Department dept = new Department("Math", "MA", 1000);
        dept.increaseBudget(500);
        assertEquals(1500.0, dept.getBudget(), 0.01);
        
        // Test negative/zero increase (should be ignored if logic exists, or handled)
        dept.increaseBudget(-100); 
        // Assuming logic prevents decrease, or just adds it. 
        // Based on code: "if (amount > 0)"
        assertEquals(1500.0, dept.getBudget(), 0.01); 
    }

    @Test
    void testDepartmentGetters() {
        // Kill any surviving getter mutants
        Department dept = new Department("History", "HIS", 500);
        assertEquals("History", dept.getDeptName());
        assertEquals("HIS", dept.getDeptCode());
        assertNull(dept.getHeadOfDept());
    }
}