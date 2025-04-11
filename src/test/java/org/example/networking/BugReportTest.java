package org.example.networking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BugReportTest {
    @Test
    void testConstructorAndGetters() {
        bugReport report = new bugReport("UI Bug", "Submit button doesn't work");

        assertEquals("UI Bug", report.getType());
        assertEquals("Submit button doesn't work", report.getComment());
    }

    @Test
    void testSetters() {
        bugReport report = new bugReport("Placeholder", "Placeholder");

        report.setType("Game Logic Issue");
        report.setComment("Game ends unexpectedly");

        assertEquals("Game Logic Issue", report.getType());
        assertEquals("Game ends unexpectedly", report.getComment());
    }
}
