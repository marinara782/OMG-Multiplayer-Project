package org.example.utilities;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import java.awt.*;
import java.util.*;

public class ChatManager {
    public static class TicTacToeBot extends ChatManager {
        private static final Random random = new Random();

        // Response categories
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

        // Generate a response with a delay and simulate typing
        public static void generateResponseWithDelay(String input, javafx.scene.control.TextArea chatDisplay) {
            String response = generateResponse(input);
            String botMessagePrefix = "\nComputer: ";

            new Thread(() -> {
                try {
                    // Show "Computer: Typing" with animated dots
                    for (int i = 0; i < 6; i++) { // Animate for about 1.8 seconds (6 * 300ms)
                        final String typingText = botMessagePrefix + "Typing" + ".".repeat(i % 4);
                        Platform.runLater(() -> {
                            String text = chatDisplay.getText();
                            int lastNewline = text.lastIndexOf("\nComputer: ");
                            if (lastNewline != -1) {
                                chatDisplay.setText(text.substring(0, lastNewline) + typingText);
                            } else {
                                chatDisplay.appendText(typingText);
                            }
                        });
                        Thread.sleep(300); // Adjust speed of animation
                    }

                    // Replace "Typing..." with actual message
                    Platform.runLater(() -> {
                        String text = chatDisplay.getText();
                        int lastNewline = text.lastIndexOf("\nComputer: ");
                        if (lastNewline != -1) {
                            chatDisplay.setText(text.substring(0, lastNewline) + botMessagePrefix);
                        }
                    });

                    // Typing effect: revealing the response one character at a time
                    for (int i = 0; i < response.length(); i++) {
                        final String partialText = botMessagePrefix + response.substring(0, i + 1);
                        Platform.runLater(() -> {
                            String text = chatDisplay.getText();
                            int lastNewline = text.lastIndexOf("\nComputer: ");
                            if (lastNewline != -1) {
                                chatDisplay.setText(text.substring(0, lastNewline) + partialText);
                            }
                        });
                        Thread.sleep(100); // Adjust speed of typing
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        public static String generateResponse(String input) {
            input = input.toLowerCase();

            if (input.contains("win")) {
                return randomResponse(winResponses);
            } else if (input.contains("block")) {
                return randomResponse(blockResponses);
            } else if (input.matches("[a-c][1-3]")) {  // Recognizing grid positions like A1, B2
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
            System.out.println("Tic-Tac-Toe Bot Ready! Type moves like 'A1', 'win', 'block', or 'restart':");

            while (true) {
                System.out.print("> ");
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("exit")) break;
                System.out.println(generateResponse(userInput));
            }
            scanner.close();
        }
    }
}
