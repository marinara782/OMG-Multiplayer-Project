package org.example.utilities;

import java.util.*;

public class GameTimer {
    private long phaseStartTime;
    private long pausedTime;
    private boolean isRunning;
    private boolean isPaused;
    private long timeLimitMs = 30000; // Default 30s per phase
    private long totalElapsedTime = 0;
    private int currentPhase = 0;
    private String phaseLabel = "Turn";

    private Thread autoEndThread;
    private boolean autoEndEnabled = false;
    private final Map<Integer, Long> phaseDurations = new LinkedHashMap<>();

    public GameTimer() {}

    // CONFIG
    public void setPhaseTimeLimitSeconds(int seconds) {
        this.timeLimitMs = seconds * 1000L;
        System.out.println("[TIMER] Phase time limit set to " + seconds + " seconds.");
    }

    public void setPhaseLabel(String label) {
        this.phaseLabel = label;
    }

    // TIMER

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

    public void pausePhase() {
        if (isRunning && !isPaused) {
            pausedTime = System.currentTimeMillis() - phaseStartTime;
            isPaused = true;
            System.out.println("[TIMER] " + phaseLabel + " paused.");
        }
    }

    public void resumePhase() {
        if (isPaused) {
            phaseStartTime = System.currentTimeMillis() - pausedTime;
            isPaused = false;
            System.out.println("[TIMER] " + phaseLabel + " resumed.");
        }
    }

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

    //STATUS

    public boolean isTimerRunning() {
        return isRunning;
    }

    public boolean isTimerPaused() {
        return isPaused;
    }

    public int getCurrentPhaseElapsedSeconds() {
        if (!isRunning) return 0;
        long elapsed = isPaused ? pausedTime : System.currentTimeMillis() - phaseStartTime;
        return (int) (elapsed / 1000);
    }

    public String getFormattedPhaseTime() {
        int sec = getCurrentPhaseElapsedSeconds();
        return String.format("%02d:%02d", sec / 60, sec % 60);
    }

    public long getTotalElapsedSeconds() {
        return totalElapsedTime / 1000;
    }

    public boolean hasTimedOut() {
        if (!isRunning || timeLimitMs <= 0) return false;
        long elapsed = isPaused ? pausedTime : System.currentTimeMillis() - phaseStartTime;
        return elapsed >= timeLimitMs;
    }

    public Map<Integer, Long> getPhaseDurations() {
        return phaseDurations;
    }

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

    // AUTO-END SUPPORT

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

    public void disableAutoEnd() {
        autoEndEnabled = false;
        if (autoEndThread != null) {
            autoEndThread.interrupt();
            autoEndThread = null;
        }
        System.out.println("[TIMER] Auto-end disabled.");
    }
}
