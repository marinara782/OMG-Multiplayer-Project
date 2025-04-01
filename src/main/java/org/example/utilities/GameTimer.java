package org.example.utilities;

import java.util.*;

public class GameTimer {
    private long turnStartTime;
    private long pausedTime;
    private boolean isRunning;
    private boolean isPaused;
    private long turnTimeLimitMs = 30000; // default 30 sec
    private long totalElapsedTime = 0;
    private int currentTurn = 0;

    private final Map<Integer, Long> turnDurations = new LinkedHashMap<>(); // Turn#: Duration

    public GameTimer() {
        this.isRunning = false;
        this.isPaused = false;
    }

    // ====== TURN TIMER ======

    public void startNewTurn() {
        if (isRunning) {
            endCurrentTurn(); // Log the previous turn before starting new one
        }
        currentTurn++;
        turnStartTime = System.currentTimeMillis();
        isRunning = true;
        isPaused = false;
        pausedTime = 0;
        System.out.println("[TIMER] Turn " + currentTurn + " started.");
    }

    public void endCurrentTurn() {
        if (isRunning) {
            long duration = isPaused ? pausedTime : (System.currentTimeMillis() - turnStartTime);
            turnDurations.put(currentTurn, duration);
            totalElapsedTime += duration;
            isRunning = false;
            isPaused = false;
            System.out.println("[TIMER] Turn " + currentTurn + " ended. Duration: " + (duration / 1000) + "s");
        }
    }

    public void pauseTurn() {
        if (isRunning && !isPaused) {
            pausedTime = System.currentTimeMillis() - turnStartTime;
            isPaused = true;
            System.out.println("[TIMER] Turn paused.");
        }
    }

    public void resumeTurn() {
        if (isPaused) {
            turnStartTime = System.currentTimeMillis() - pausedTime;
            isPaused = false;
            System.out.println("[TIMER] Turn resumed.");
        }
    }

    public boolean hasTurnTimedOut() {
        if (!isRunning) return false;
        long elapsed = isPaused ? pausedTime : System.currentTimeMillis() - turnStartTime;
        return elapsed >= turnTimeLimitMs;
    }

    public int getCurrentTurnElapsedSeconds() {
        if (!isRunning) return 0;
        long elapsed = isPaused ? pausedTime : System.currentTimeMillis() - turnStartTime;
        return (int) (elapsed / 1000);
    }

    public String getFormattedTurnTime() {
        int sec = getCurrentTurnElapsedSeconds();
        return String.format("%02d:%02d", sec / 60, sec % 60);
    }

    public void setTurnTimeLimitSeconds(int seconds) {
        this.turnTimeLimitMs = seconds * 1000L;
        System.out.println("[TIMER] Turn time limit set to " + seconds + " seconds.");
    }

    public long getTotalElapsedSeconds() {
        return totalElapsedTime / 1000;
    }

    public Map<Integer, Long> getTurnDurations() {
        return turnDurations;
    }

    public void displayStatus() {
        System.out.println("========== TIMER STATUS ==========");
        System.out.println("Current Turn: " + currentTurn);
        System.out.println("Running: " + isRunning + " | Paused: " + isPaused);
        System.out.println("Elapsed This Turn: " + getFormattedTurnTime());
        System.out.println("Total Elapsed: " + getTotalElapsedSeconds() + " seconds");
        if (hasTurnTimedOut()) {
            System.out.println("[WARNING] Turn " + currentTurn + " has timed out!");
        }
        System.out.println("==================================");
    }

    public void onTurnTimeout(Runnable callback) {
        if (hasTurnTimedOut()) {
            System.out.println("[TIMER] Timeout triggered for turn " + currentTurn);
            callback.run();
        }
    }

    public void resetTimer() {
        System.out.println("[TIMER] Resetting full timer state.");
        turnStartTime = 0;
        pausedTime = 0;
        isRunning = false;
        isPaused = false;
        turnDurations.clear();
        currentTurn = 0;
        totalElapsedTime = 0;
    }
}
