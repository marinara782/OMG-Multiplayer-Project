package org.example.utilities;

import java.util.*;

public class ChatManager {
    private static final Random random = new Random();

    // Connection state
    private boolean connected = true;
    private double disconnectionProbability = 0.1;  // 10% chance
    private double packetLossProbability = 0.15;     // 15% chance
    private int latencyMaxMs = 1000;

    // ===== CONNECTION STUBS =====
    public void establishConnection() {
        System.out.println("[SYSTEM] Connecting to server...");
        connected = true;
        logEvent("Connection", "Established.");
    }

    public void terminateConnection() {
        System.out.println("[SYSTEM] Connection terminated.");
        connected = false;
        logEvent("Connection", "Terminated.");
    }

    public boolean isConnected() {
        return connected;
    }

    public void setDisconnectionProbability(double probability) {
        if (probability < 0.0 || probability > 1.0) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
        this.disconnectionProbability = probability;
    }

    public void setPacketLossProbability(double probability) {
        if (probability < 0.0 || probability > 1.0) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
        this.packetLossProbability = probability;
    }

    public void simulateLatency() {
        int delay = random.nextInt(latencyMaxMs) + 100;
        try {
            Thread.sleep(delay);
            System.out.println("[DEBUG] Simulated latency: " + delay + "ms");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[ERROR] Latency simulation interrupted.");
        }
    }

    public boolean simulateTimeout(int timeoutMs) {
        int chance = random.nextInt(100);
        if (chance < 20) {
            System.out.println("[WARNING] Request timed out after " + timeoutMs + "ms.");
            return true;
        }
        return false;
    }

    public boolean simulatePacketLoss() {
        if (random.nextDouble() < packetLossProbability) {
            System.out.println("[NETWORK] Packet lost.");
            return true;
        }
        return false;
    }

    public boolean simulateDisconnection() {
        if (random.nextDouble() < disconnectionProbability) {
            connected = false;
            handleDisconnection();
            return true;
        }
        return false;
    }

    public void handleDisconnection() {
        System.out.println("[SYSTEM] Connection lost. Attempting to reconnect...");
        logEvent("Disconnection", "Triggered by probability.");
    }

    public void retryConnection(int maxAttempts) {
        for (int i = 1; i <= maxAttempts; i++) {
            System.out.println("[RETRY] Attempt " + i + "/" + maxAttempts + "...");
            if (random.nextBoolean()) {
                establishConnection();
                return;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[FAILURE] Could not reconnect after " + maxAttempts + " attempts.");
    }

    public boolean isConnectionStable() {
        int jitter = random.nextInt(100);
        boolean stable = jitter < 50;
        System.out.println("[HEALTH CHECK] Jitter: " + jitter + "ms | Stable: " + stable);
        return stable;
    }

    public void logEvent(String eventType, String details) {
        String timestamp = new Date().toString();
        System.out.println("[LOG - " + eventType + "] " + timestamp + ": " + details);
    }

    // ===== GAME STATE STUBS =====
    public void resetBot() {
        // TODO: Reset internal state
    }

    public void notifyGameOver(boolean playerWon) {
        // TODO: Trigger final response
    }

    public void sendPlayerMove(String move) {
        // TODO: Analyze or record strategy
    }

    public void handleRestart() {
        // TODO: Reset and acknowledge
    }

    // ===== TIC TAC TOE BOT CLASS =====
    public static class TicTacToeBot extends ChatManager {
        private static final String[] moveResponses = {
                "Nice move!", "Good choice!", "You placed an X.",
                "A bold move!", "Thinking ahead, I see.", "You took the corner."
        };
        private static final String[] winResponses = {
                "You win!", "Well played!", "Game over, you won!",
                "Victory!", "You’re a Tic-Tac-Toe master!", "Brilliant strategy!"
        };
        private static final String[] blockResponses = {
                "Blocked you!", "Nice try!", "Not so fast!",
                "Defended my spot.", "That was close!", "You almost had it."
        };
        private static final String[] generalResponses = {
                "Your turn.", "What’s next?", "Pick a spot.",
                "Strategize wisely.", "The board awaits.", "I’m watching!"
        };

        public static String generateResponse(String input) {
            input = input.toLowerCase();

            if (input.contains("win")) {
                return randomResponse(winResponses);
            } else if (input.contains("block")) {
                return randomResponse(blockResponses);
            } else if (input.matches("[a-c][1-3]")) {
                return randomResponse(moveResponses);
            } else {
                return randomResponse(generalResponses);
            }
        }

        private static String randomResponse(String[] responses) {
            return responses[random.nextInt(responses.length)];
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            TicTacToeBot bot = new TicTacToeBot();
            bot.setDisconnectionProbability(0.2);
            bot.setPacketLossProbability(0.1);

            bot.establishConnection();
            System.out.println("Tic-Tac-Toe Bot Ready! Type moves like 'A1', 'win', 'block', or 'restart':");

            while (true) {
                System.out.print("> ");
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("exit")) break;

                // Simulate disconnection
                if (!bot.isConnected()) {
                    bot.retryConnection(3);
                    continue;
                }

                if (bot.simulateDisconnection()) continue;
                if (bot.simulatePacketLoss()) continue;
                if (bot.simulateTimeout(800)) continue;

                bot.simulateLatency();
                System.out.println(generateResponse(userInput));
            }

            scanner.close();
            bot.terminateConnection();
        }
    }
}
