package com.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {
    @Test
    void testMainExecution() {
        // This runs the main method to ensure the "Smoke Test" passes
        // and to get line coverage for the Main class.
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}