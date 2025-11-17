package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void testNotNullOrEmpty() {
        assertTrue(ValidationUtils.isNotNullOrEmpty("Valid"));
        assertFalse(ValidationUtils.isNotNullOrEmpty(null));
        assertFalse(ValidationUtils.isNotNullOrEmpty("   "));
        assertFalse(ValidationUtils.isNotNullOrEmpty(""));
    }

    @Test
    void testValidLength() {
        assertTrue(ValidationUtils.isValidLength("abc", 1, 5));
        assertFalse(ValidationUtils.isValidLength("abcdef", 1, 5)); // Too long
        assertFalse(ValidationUtils.isValidLength("", 1, 5)); // Too short
        assertFalse(ValidationUtils.isValidLength(null, 1, 5)); // Null check
    }

    @Test
    void testValidEmail() {
        assertTrue(ValidationUtils.isValidEmail("test@univ.edu"));
        assertFalse(ValidationUtils.isValidEmail("invalid-email"));
        assertFalse(ValidationUtils.isValidEmail(null));
        assertFalse(ValidationUtils.isValidEmail("")); 
    }

    @Test
    void testValidId() {
        assertTrue(ValidationUtils.isValidId("S123"));
        assertTrue(ValidationUtils.isValidId("F9999"));
        assertFalse(ValidationUtils.isValidId("123")); // Missing prefix
        assertFalse(ValidationUtils.isValidId("S12")); // Too short
        assertFalse(ValidationUtils.isValidId(null));
    }

    @Test
    void testValidName() {
        assertTrue(ValidationUtils.isValidName("John Doe"));
        assertTrue(ValidationUtils.isValidName("O'Connor"));
        assertFalse(ValidationUtils.isValidName("John123")); // Numbers
        assertFalse(ValidationUtils.isValidName(null));
    }

    @Test
    void testValidAge() {
        assertTrue(ValidationUtils.isValidAge(20));
        assertFalse(ValidationUtils.isValidAge(15)); // Boundary < 16
        assertFalse(ValidationUtils.isValidAge(101)); // Boundary > 100
    }

    @Test
    void testValidGpa() {
        assertTrue(ValidationUtils.isValidGpa(3.5));
        assertTrue(ValidationUtils.isValidGpa(0.0));
        assertTrue(ValidationUtils.isValidGpa(4.0));
        assertFalse(ValidationUtils.isValidGpa(-0.1));
        assertFalse(ValidationUtils.isValidGpa(4.1));
    }

    @Test
    void testValidCredit() {
        assertTrue(ValidationUtils.isValidCredit(3));
        assertFalse(ValidationUtils.isValidCredit(0));
        assertFalse(ValidationUtils.isValidCredit(-1));
    }
    
    @Test
    void testLogging() {
        // Just to cover the lines, even though they just print to console
        assertDoesNotThrow(() -> ValidationUtils.logError("Test", "Error msg"));
        assertDoesNotThrow(() -> ValidationUtils.logInfo("Test", "Info msg"));
    }
}