package org.example.utilities;
import javafx.application.Platform;
import java.util.*;

public class ChatManager {

    public static class TicTacToeBot extends ChatManager {
        private static final Random random = new Random();

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
    }

    public static class CheckersBot extends ChatManager {
        private static final Random random = new Random();

        private static final String[] jumpResponses = {
                "Jumped over you!", "Got your piece!", "Sneaky move!",
                "Boom! Double jump!", "Captured a piece!"
        };
        private static final String[] kingResponses = {
                "Crowned a king!", "Long live the king!", "You’ve reached the end!",
                "Now I’m royalty!"
        };
        private static final String[] generalResponses = {
                "Your move.", "Don’t let me trap you!", "What’s your strategy?",
                "Let’s dance!", "You planning something?", "I see your move."
        };

        public static String generateResponse(String input) {
            input = input.toLowerCase();

            if (input.contains("jump") || input.contains("capture")) {
                return randomResponse(jumpResponses);
            } else if (input.contains("king")) {
                return randomResponse(kingResponses);
            } else {
                return randomResponse(generalResponses);
            }
        }

        private static String randomResponse(String[] responses) {
            return responses[random.nextInt(responses.length)];
        }
    }

    public static class ConnectFourBot extends ChatManager {
        private static final Random random = new Random();

        private static final String[] connectResponses = {
                "Four in a row!", "Nice drop!", "That column is mine!",
                "Connect four!", "You’re getting close!"
        };
        private static final String[] blockResponses = {
                "Blocked you!", "Not today!", "Nice try!",
                "Cut you off!", "Denied!"
        };
        private static final String[] generalResponses = {
                "Your move.", "Drop it wisely.", "Pick a column.",
                "I’m watching you!", "Game’s heating up!"
        };

        public static String generateResponse(String input) {
            input = input.toLowerCase();

            if (input.contains("connect") || input.contains("win")) {
                return randomResponse(connectResponses);
            } else if (input.contains("block")) {
                return randomResponse(blockResponses);
            } else if (input.matches("[1-7]")) {
                return "Dropped in column " + input + ". " + randomResponse(generalResponses);
            } else {
                return randomResponse(generalResponses);
            }
        }

        private static String randomResponse(String[] responses) {
            return responses[random.nextInt(responses.length)];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose your game: [tic-tac-toe / checkers / connect-four]");
        System.out.print("> ");
        String gameChoice = scanner.nextLine().toLowerCase();

        System.out.println("Type moves (e.g. A1, 'win', 'block', 'jump', '4') or 'exit' to quit:");

        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) break;

            String response;
            switch (gameChoice) {
                case "tic-tac-toe":
                    response = TicTacToeBot.generateResponse(userInput);
                    break;
                case "checkers":
                    response = CheckersBot.generateResponse(userInput);
                    break;
                case "connectFour":
                case "connect four":
                case "connect-four":
                    response = ConnectFourBot.generateResponse(userInput);
                    break;
                default:
                    response = "Unknown game. Please restart and choose a valid game.";
            }
            System.out.println(response);
        }

        scanner.close();
    }
}

