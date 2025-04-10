package org.example.utilities;

import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {

    private GameTimer timer;

    @BeforeEach
    void setUp() {
        timer = new GameTimer();
    }

    @Test
    void testStartAndEndPhase() throws InterruptedException {
        timer.startNewPhase();
        Thread.sleep(100); // Simulate time passing
        timer.endCurrentPhase();

        Map<Integer, Long> durations = timer.getPhaseDurations();
        assertEquals(1, durations.size());
        assertTrue(durations.containsKey(1));
        assertTrue(durations.get(1) >= 100); // Should be at least 100ms
    }

    @Test
    void testPauseAndResume() throws InterruptedException {
        timer.startNewPhase();
        Thread.sleep(100);
        timer.pausePhase();

        int pausedElapsed = timer.getCurrentPhaseElapsedSeconds();
        Thread.sleep(100); // Shouldn't count while paused
        assertEquals(pausedElapsed, timer.getCurrentPhaseElapsedSeconds());

        timer.resumePhase();
        Thread.sleep(100);
        timer.endCurrentPhase();

        assertTrue(timer.getPhaseDurations().get(1) >= 200); // Includes time before and after pause
    }

    @Test
    void testReset() {
        timer.startNewPhase();
        timer.endCurrentPhase();
        timer.resetTimer();

        assertFalse(timer.isTimerRunning());
        assertEquals(0, timer.getTotalElapsedSeconds());
        assertEquals(0, timer.getPhaseDurations().size());
    }

    @Test
    void testTimeoutDetection() throws InterruptedException {
        timer.setPhaseTimeLimitSeconds(1); // 1 second timeout
        timer.startNewPhase();
        Thread.sleep(1100);

        assertTrue(timer.hasTimedOut());
    }

    @Test
    void testAutoEndCallsCallback() throws InterruptedException {
        AtomicBoolean callbackCalled = new AtomicBoolean(false);

        timer.setPhaseTimeLimitSeconds(1);
        timer.startNewPhase();

        timer.enableAutoEnd(() -> callbackCalled.set(true), 200);

        Thread.sleep(1500); // Wait for auto-end
        assertTrue(callbackCalled.get());
        assertFalse(timer.isTimerRunning()); // Should have auto-ended
    }

    @Test
    void testSetPhaseLabel() {
        timer.setPhaseLabel("Round");
        timer.startNewPhase();
        timer.endCurrentPhase();
        // Can't easily test the label directly, but can assume it runs without error
    }

    @Test
    void testGetFormattedPhaseTime() throws InterruptedException {
        timer.startNewPhase();
        Thread.sleep(1000);
        int seconds = timer.getCurrentPhaseElapsedSeconds();
        String formatted = timer.getFormattedPhaseTime();

        assertTrue(formatted.matches("\\d{2}:\\d{2}"));
        assertEquals(String.format("%02d:%02d", seconds / 60, seconds % 60), formatted);
    }
}
