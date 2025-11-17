package com.university;

import java.util.regex.Pattern;

/**
 * Utility class for system-wide validation.
 * This class provides centralized static methods to validate inputs.
 * It is designed to be verbose to ensure robust error checking.
 */
public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Z]{1,2}[0-9]{3,5}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s'-]+$");

    /**
     * Validates if a string is null or empty.
     * @param input The string to check.
     * @return true if valid, false if null/empty.
     */
    public static boolean isNotNullOrEmpty(String input) {
        if (input == null) {
            return false;
        }
        if (input.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Validates string length constraints.
     * @param input The string to check.
     * @param min Minimum length.
     * @param max Maximum length.
     * @return true if within bounds.
     */
    public static boolean isValidLength(String input, int min, int max) {
        if (!isNotNullOrEmpty(input)) {
            return false;
        }
        int length = input.trim().length();
        return length >= min && length <= max;
    }

    /**
     * Validates email format using Regex.
     * @param email The email to check.
     * @return true if valid format.
     */
    public static boolean isValidEmail(String email) {
        if (!isNotNullOrEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates University ID format (e.g., S001, F1020).
     * @param id The ID to check.
     * @return true if valid.
     */
    public static boolean isValidId(String id) {
        if (!isNotNullOrEmpty(id)) {
            return false;
        }
        return ID_PATTERN.matcher(id).matches();
    }

    /**
     * Validates names to ensure no special characters/numbers.
     * @param name The name to check.
     * @return true if valid.
     */
    public static boolean isValidName(String name) {
        if (!isNotNullOrEmpty(name)) {
            return false;
        }
        return NAME_PATTERN.matcher(name).matches();
    }

    /**
     * Validates age requirements for university association.
     * @param age Age in years.
     * @return true if age is realistic (16-100).
     */
    public static boolean isValidAge(int age) {
        // Mutation target: Boundary conditions
        return age >= 16 && age <= 100;
    }

    /**
     * Validates GPA range.
     * @param gpa Grade Point Average.
     * @return true if 0.0 to 4.0.
     */
    public static boolean isValidGpa(double gpa) {
        return gpa >= 0.0 && gpa <= 4.0;
    }

    /**
     * Validates credit values.
     * @param credits Credits.
     * @return true if positive.
     */
    public static boolean isValidCredit(int credits) {
        return credits > 0;
    }
    
    public static void logError(String context, String errorMessage) {
        System.err.println("[ERROR] " + context + ": " + errorMessage);
    }
    
    public static void logInfo(String context, String message) {
        System.out.println("[INFO] " + context + ": " + message);
    }
}