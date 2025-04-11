package org.example.utilities;

import java.util.*;

public class GameTimer {

    // Time and state tracking variables
    private long phaseStartTime;
    private long pausedTime;
    private boolean isRunning;
    private boolean isPaused;
    private long timeLimitMs = 30000; // Default phase time = 30 seconds
    private long totalElapsedTime = 0;
    private int currentPhase = 0;
    private String phaseLabel = "Turn";

    // Auto-end support
    private Thread autoEndThread;
    private boolean autoEndEnabled = false;

    // Stores the duration of each phase
    private final Map<Integer, Long> phaseDurations = new LinkedHashMap<>();

    public GameTimer() {}

    // Set how long each phase should last (in seconds)
    public void setPhaseTimeLimitSeconds(int seconds) {
        this.timeLimitMs = seconds * 1000L;
        System.out.println("[TIMER] Phase time limit set to " + seconds + " seconds.");
    }

    // Set custom name for phases (e.g. "Round", "Turn")
    public void setPhaseLabel(String label) {
        this.phaseLabel = label;
    }

    // Start a new phase (automatically ends previous one if still running)
    public void startNewPhase() {
        if (isRunning) {
            endCurrentPhase();
        }
        currentPhase++;
        phaseStartTime = System.currentTimeMillis();
        isRunning = true;
        isPaused = false;
        pausedTime = 0;
        System.out.println("[TIMER] " + phaseLabel + " " + currentPhase + " started.");
    }

    // End the current phase and save its duration
    public void endCurrentPhase() {
        if (isRunning) {
            long duration = isPaused ? pausedTime : (System.currentTimeMillis() - phaseStartTime);
            phaseDurations.put(currentPhase, duration);
            totalElapsedTime += duration;
            isRunning = false;
            isPaused = false;
            System.out.println("[TIMER] " + phaseLabel + " " + currentPhase + " ended. Duration: " + (duration / 1000) + "s");
        }
    }

    // Pause the current phase
    public void pausePhase() {
        if (isRunning && !isPaused) {
            pausedTime = System.currentTimeMillis() - phaseStartTime;
            isPaused = true;
            System.out.println("[TIMER] " + phaseLabel + " paused.");
        }
    }

    // Resume the current phase
    public void resumePhase() {
        if (isPaused) {
            phaseStartTime = System.currentTimeMillis() - pausedTime;
            isPaused = false;
            System.out.println("[TIMER] " + phaseLabel + " resumed.");
        }
    }

    // Reset everything back to default
    public void resetTimer() {
        phaseStartTime = 0;
        pausedTime = 0;
        isRunning = false;
        isPaused = false;
        totalElapsedTime = 0;
        currentPhase = 0;
        phaseDurations.clear();
        disableAutoEnd();
        System.out.println("[TIMER] Reset complete.");
    }

    // Check if the timer is currently running
    public boolean isTimerRunning() {
        return isRunning;
    }

    // Check if the timer is currently paused
    public boolean isTimerPaused() {
        return isPaused;
    }

    // Get how many seconds have passed in the current phase
    public int getCurrentPhaseElapsedSeconds() {
        if (!isRunning) return 0;
        long elapsed = isPaused ? pausedTime : System.currentTimeMillis() - phaseStartTime;
        return (int) (elapsed / 1000);
    }

    // Get current phase time in MM:SS format
    public String getFormattedPhaseTime() {
        int sec = getCurrentPhaseElapsedSeconds();
        return String.format("%02d:%02d", sec / 60, sec % 60);
    }

    // Total time across all completed phases
    public long getTotalElapsedSeconds() {
        return totalElapsedTime / 1000;
    }

    // Check if current phase has exceeded the time limit
    public boolean hasTimedOut() {
        if (!isRunning || timeLimitMs <= 0) return false;
        long elapsed = isPaused ? pausedTime : System.currentTimeMillis() - phaseStartTime;
        return elapsed >= timeLimitMs;
    }

    // Get a map of phase numbers and their durations
    public Map<Integer, Long> getPhaseDurations() {
        return phaseDurations;
    }

    // Print current timer status to console
    public void displayStatus() {
        System.out.println("======== TIMER STATUS ========");
        System.out.println("Phase Label   : " + phaseLabel);
        System.out.println("Current " + phaseLabel + ": " + currentPhase);
        System.out.println("Running       : " + isRunning + " | Paused: " + isPaused);
        System.out.println("Elapsed Time  : " + getFormattedPhaseTime());
        System.out.println("Total Elapsed : " + getTotalElapsedSeconds() + " seconds");
        if (hasTimedOut()) {
            System.out.println("⚠️ [WARNING] Phase timed out!");
        }
        System.out.println("=============================");
    }

    // Auto-end: runs a task if phase times out
    public void enableAutoEnd(Runnable onTimeoutCallback, int checkIntervalMs) {
        if (!isRunning || autoEndEnabled) return;

        autoEndEnabled = true;
        autoEndThread = new Thread(() -> {
            try {
                while (isRunning && autoEndEnabled) {
                    Thread.sleep(checkIntervalMs);
                    if (hasTimedOut()) {
                        System.out.println("[TIMER] Auto timeout reached.");
                        onTimeoutCallback.run();
                        endCurrentPhase();
                        autoEndEnabled = false;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        autoEndThread.setDaemon(true);
        autoEndThread.start();
        System.out.println("[TIMER] Auto-end enabled.");
    }

    // Stop the auto-end feature
    public void disableAutoEnd() {
        autoEndEnabled = false;
        if (autoEndThread != null) {
            autoEndThread.interrupt();
            autoEndThread = null;
        }
        System.out.println("[TIMER] Auto-end disabled.");
    }

    // Unused placeholder method
    public int getElapsedSeconds() {
        return 0;
    }
}
