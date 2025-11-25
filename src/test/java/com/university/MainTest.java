package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Smoke test for the Main class.
 * Ensures the main method executes without throwing any exceptions.
 */
class MainTest {

    @Test
    void testMainExecution() {
        // Executes the application's entry point to ensure
        // no runtime errors occur during basic startup.
        // Helps achieve line coverage for Main.main().
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
