package org.example.networking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BugReportTest {

    // Test that the constructor correctly assigns values, and getters return them
    @Test
    void testConstructorAndGetters() {
        bugReport report = new bugReport("UI Bug", "Submit button doesn't work");

        assertEquals("UI Bug", report.getType());          // check bug type is stored correctly
        assertEquals("Submit button doesn't work", report.getComment());  // check comment is stored correctly
    }

    // Test that setters correctly update the fields
    @Test
    void testSetters() {
        bugReport report = new bugReport("Placeholder", "Placeholder");

        report.setType("Game Logic Issue");               // change bug type
        report.setComment("Game ends unexpectedly");      // change comment

        assertEquals("Game Logic Issue", report.getType());     // verify updated type
        assertEquals("Game ends unexpectedly", report.getComment()); // verify updated comment
    }
}
