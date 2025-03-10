package org.example.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.authentication.UserProfile;
import org.example.game.checkers.CheckersBoard;
import org.example.game.checkers.CheckersGame;
import org.example.game.connectFour.ConnectFourBoard;
import org.example.game.connectFour.ConnectFourGame;
import org.example.game.connectFour.ConnectFourGameModeSelection;
import org.example.game.ticTacToe.TicTacToeBoard;
import org.example.game.ticTacToe.TicTacToeGame;
import org.example.networking.GameSession;
import org.example.utilities.ChatManager;
import org.example.utilities.GameTimer;
import javafx.scene.media.AudioClip;

import java.io.File;

public class GameWindow {
    private final Stage stage;
    private final UserProfile currentUser;
    private final Object gameInstance;
    private GameSession gameSession;
    private ChatManager chatManager;
    private final GameTimer gameTimer;
    private Timeline updateTimeline;
    private VBox gameBoard;
    private Label timerLabel;


    public GameWindow(Stage stage, Object gameInstance, UserProfile currentUser) {
        this.stage = stage;
        this.gameInstance = gameInstance;
        this.currentUser = currentUser;
        this.gameSession = new GameSession();
        this.gameTimer = new GameTimer();

        // Initialize chatManager based on selected game
        if (gameInstance instanceof TicTacToeGame) {
            this.chatManager = new ChatManager.TicTacToeBot(); // Use the TicTacToeBot//
        } else {
            this.chatManager = new ChatManager(); // Use the default ChatManager
        }

        initializeUI();
        setupGameBoard();
        startGameUpdates();
    }

    private void initializeUI() {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(15));
        mainLayout.setStyle("-fx-background-color: #2c3e50;");

        // Top section - Game info and controls
        HBox topBar = createTopBar();
        mainLayout.setTop(topBar);

        // Center section - Game board placeholder (will be set in setupGameBoard)
        gameBoard = new VBox();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5;");
        mainLayout.setCenter(gameBoard);

        // Right section - Chat and player info
        VBox rightPanel = createRightPanel();
        mainLayout.setRight(rightPanel);

        // Bottom section - Game controls
        HBox bottomBar = createBottomBar();
        mainLayout.setBottom(bottomBar);

        Scene scene = new Scene(mainLayout, 1200, 800);
        stage.setTitle(getGameTitle() + " - OMG Platform");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #1a2530; -fx-background-radius: 5;");

        // Game title
        Label gameTitle = new Label(getGameTitle());
        gameTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #3498db;");

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Turn indicator
        Label turnLabel = new Label("Your Turn");
        turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");

        // Timer
        timerLabel = new Label("0:30");
        timerLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Return to main menu button
        Button exitButton = new Button("Exit Game");
        exitButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        exitButton.setOnAction(e -> confirmExit());

        topBar.getChildren().addAll(gameTitle, spacer, turnLabel, timerLabel, exitButton);

        return topBar;
    }

    private VBox createRightPanel() {
        VBox rightPanel = new VBox(20);
        rightPanel.setPadding(new Insets(10, 0, 10, 20));
        rightPanel.setPrefWidth(300);

        // Opponent info section
        VBox opponentInfo = new VBox(10);
        opponentInfo.setPadding(new Insets(15));
        opponentInfo.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5;");

        Label opponentTitle = new Label("Opponent");
        opponentTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox opponentDetails = new HBox(10);
        opponentDetails.setAlignment(Pos.CENTER_LEFT);

        // Opponent avatar placeholder
        Region avatarPlaceholder = new Region();
        avatarPlaceholder.setPrefSize(50, 50);
        avatarPlaceholder.setStyle("-fx-background-color: #3498db; -fx-background-radius: 25;");

        VBox opponentStats = new VBox(5);
        Label opponentName = new Label("PlayerXYZ");
        opponentName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label opponentRank = new Label("Rank: 1242");
        opponentRank.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");

        Label opponentWins = new Label("Win Rate: 65%");
        opponentWins.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");

        opponentStats.getChildren().addAll(opponentName, opponentRank, opponentWins);
        opponentDetails.getChildren().addAll(avatarPlaceholder, opponentStats);

        opponentInfo.getChildren().addAll(opponentTitle, opponentDetails);

        // Game stats section
        VBox gameStats = new VBox(10);
        gameStats.setPadding(new Insets(15));
        gameStats.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5;");

        Label statsTitle = new Label("Game Stats");
        statsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        GridPane stats = new GridPane();
        stats.setHgap(10);
        stats.setVgap(8);

        stats.add(new Label("Moves:"), 0, 0);
        stats.add(new Label("12"), 1, 0);
        stats.add(new Label("Time Elapsed:"), 0, 1);
        stats.add(new Label("3:45"), 1, 1);
        stats.add(new Label("Game Type:"), 0, 2);
        stats.add(new Label("Ranked"), 1, 2);

        // Style all labels in the stats grid
        stats.getChildren().forEach(node -> {
            if (node instanceof Label) {
                ((Label) node).setStyle("-fx-text-fill: white;");
            }
        });

        gameStats.getChildren().addAll(statsTitle, stats);

        // Chat section
        VBox chatSection = new VBox(10);
        chatSection.setPadding(new Insets(15));
        chatSection.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5;");

        // Create a child node (for example, a Label)
        Label chatLabel = new Label("Chat Section");

        // Set the vertical grow priority for the child node
        VBox.setVgrow(chatLabel, Priority.ALWAYS);

        // Add the child node to the chatSection VBox
        chatSection.getChildren().add(chatLabel);


        Label chatTitle = new Label("Chat");
        chatTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        TextArea chatDisplay = new TextArea();
        chatDisplay.setEditable(false);
        chatDisplay.setPrefHeight(200);
        chatDisplay.setStyle("-fx-control-inner-background: #1a2530; -fx-text-fill: white;");
        chatDisplay.setText("System: Game started\nComputer: good luck!\nYou: you too!");
        VBox.setVgrow(chatDisplay, Priority.ALWAYS);

        HBox chatInputBox = new HBox(10);
        TextField chatInput = new TextField();
        chatInput.setPromptText("Type your message...");
        chatInput.setStyle("-fx-background-color: #1a2530; -fx-text-fill: white;");
        HBox.setHgrow(chatInput, Priority.ALWAYS);

        Button sendButton = getButton(chatInput, chatDisplay, gameInstance);

        chatInputBox.getChildren().addAll(chatInput, sendButton);

        chatSection.getChildren().addAll(chatTitle, chatDisplay, chatInputBox);

        rightPanel.getChildren().addAll(opponentInfo, gameStats, chatSection);

        return rightPanel;
    }

    private static Button getButton(TextField chatInput, TextArea chatDisplay, Object gameInstance) {
        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        sendButton.setOnAction(e -> {
            String message = chatInput.getText().trim();
            if (!message.isEmpty()) {
                chatDisplay.appendText("\nYou: " + message);

                if (gameInstance instanceof TicTacToeGame) {
                    // Show "Typing..." message first
                    chatDisplay.appendText("\nBot: Typing");

                    // Create a timeline to simulate typing effect
                    Timeline typingAnimation = new Timeline(
                            // First cycle of animation
                            new KeyFrame(Duration.seconds(0.25), evt -> chatDisplay.setText(
                                    chatDisplay.getText().replace("Typing", "Typing."))),
                            new KeyFrame(Duration.seconds(0.5), evt -> chatDisplay.setText(
                                    chatDisplay.getText().replace("Typing.", "Typing.."))),
                            new KeyFrame(Duration.seconds(0.75), evt -> chatDisplay.setText(
                                    chatDisplay.getText().replace("Typing..", "Typing..."))),
                            // Second cycle of animation
                            new KeyFrame(Duration.seconds(1), evt -> chatDisplay.setText(
                                    chatDisplay.getText().replace("Typing...", "Typing"))),
                            new KeyFrame(Duration.seconds(1.25), evt -> chatDisplay.setText(
                                    chatDisplay.getText().replace("Typing", "Typing."))),
                            new KeyFrame(Duration.seconds(1.5), evt -> chatDisplay.setText(
                                    chatDisplay.getText().replace("Typing.", "Typing.."))),
                            new KeyFrame(Duration.seconds(1.75), evt -> chatDisplay.setText(
                                    chatDisplay.getText().replace("Typing..", "Typing...")))
                    );

                    // After animation, replace "Typing..." with actual response
                    typingAnimation.setOnFinished(evt -> {
                        String response = ChatManager.TicTacToeBot.generateResponse(message);

                        Platform.runLater(() -> {
                            String currentText = chatDisplay.getText();

                            // More robust way to replace "Typing..." with the response
                            int typingIndex = currentText.lastIndexOf("Bot: Typing...");
                            if (typingIndex >= 0) {
                                // Split the text into before and after "Typing..."
                                String textBeforeTyping = currentText.substring(0, typingIndex);
                                String textAfterTyping = currentText.substring(typingIndex + 14); // 9 is length of "Typing..."

                                // Reconstruct the text with the new response
                                String updatedText = textBeforeTyping + "Bot: " + response + textAfterTyping;
                                chatDisplay.setText(updatedText);
                            } else {
                                // Fallback if "Typing..." is not found
                                chatDisplay.appendText("\nBot: " + response);
                            }
                        });
                    });

                    typingAnimation.play();  // Start animation
                }
                chatInput.clear();
            }
        });

        return sendButton;
    }

    private HBox createBottomBar() {
        HBox bottomBar = new HBox(15);
        bottomBar.setPadding(new Insets(10, 5, 5, 5));
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setStyle("-fx-background-color: #1a2530; -fx-background-radius: 5;");

        Button undoButton = new Button("Undo");
        undoButton.setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white;");
        undoButton.setDisable(true); // Only enable in some games or situations

        Button drawButton = new Button("Offer Draw");
        drawButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        drawButton.setOnAction(e -> offerDraw());

        Button resignButton = new Button("Resign");
        resignButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        resignButton.setOnAction(e -> confirmResign());

        bottomBar.getChildren().addAll(undoButton, drawButton, resignButton);

        return bottomBar;
    }

    private void setupGameBoard() {
        System.out.println("Setting up game board, instance: " + gameInstance);
        System.out.println("Is TicTacToe: " + (gameInstance instanceof TicTacToeGame));
        System.out.println("Is ConnectFour: " + (gameInstance instanceof ConnectFourGame));
        System.out.println("Is Checkers: " + (gameInstance instanceof CheckersGame));

        gameBoard.getChildren().clear();

        if (gameInstance instanceof TicTacToeGame) {
            System.out.println("Setting up TicTacToe board");

            TicTacToeBoard ticTacToeBoard = new TicTacToeBoard((TicTacToeGame) gameInstance, stage, currentUser);
            // Add the TicTacToeBoard to the gameBoard VBox
            gameBoard.getChildren().add(ticTacToeBoard);
        } else if (gameInstance instanceof ConnectFourGame) {
            System.out.println("Setting up ConnectFour board");
            ConnectFourGameModeSelection connectFourGameModeSelection = new ConnectFourGameModeSelection(stage, currentUser);

            ConnectFourBoard connectFourBoard = new ConnectFourBoard((ConnectFourGame) gameInstance, stage, currentUser, connectFourGameModeSelection.playAgainstComputer, connectFourGameModeSelection.playerIsRed, connectFourGameModeSelection.difficulty);
            gameBoard.getChildren().add(connectFourBoard);
        } else if (gameInstance instanceof CheckersGame) {
            System.out.println("Setting up Checkers board");

            CheckersBoard checkersBoard = new CheckersBoard((CheckersGame) gameInstance, stage, currentUser);
            gameBoard.getChildren().add(checkersBoard);
        }
    }

    private String getGameTitle() {
        if (gameInstance instanceof TicTacToeGame) {
            return "Tic-Tac-Toe";
        } else if (gameInstance instanceof ConnectFourGame) {
            return "Connect Four";
        } else if (gameInstance instanceof CheckersGame) {
            return "Checkers";
        }
        return "Game";
    }

    private void startGameUpdates() {
        updateTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateGameState())
        );
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    private void updateGameState() {
        // Update timer display
        int seconds = gameTimer.getElapsedSeconds();
        int minutes = seconds / 60;
        seconds = seconds % 60;
        timerLabel.setText(String.format("%d:%02d", minutes, seconds));
    }

    // Dialog methods
    private void confirmExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("You will forfeit the current game if you exit now.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                returnToMainMenu();
            }
        });
    }

    private void offerDraw() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Offer Draw");
        alert.setHeaderText("Do you want to offer a draw?");
        alert.setContentText("Your opponent will be asked if they want to accept a draw.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Simulate opponent accepting draw
                showDrawAcceptedDialog();
            }
        });
    }

    private void showDrawAcceptedDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Draw Accepted");
        alert.setHeaderText("Draw Accepted");
        alert.setContentText("Your opponent has accepted your draw offer. The game ends in a draw.");

        alert.showAndWait().ifPresent(response -> returnToMainMenu());
    }

    private void confirmResign() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Resign Game");
        alert.setHeaderText("Are you sure you want to resign?");
        alert.setContentText("This will count as a loss in your record.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                showGameOverDialog("Resigned", false);
            }
        });
    }

    private void showGameOverDialog(String reason, boolean victory) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(victory ? "Victory!" : "Defeat");
        alert.setContentText("Game over: " + reason);

        alert.showAndWait().ifPresent(response -> returnToMainMenu());
    }

    private void returnToMainMenu() {
        if (updateTimeline != null) {
            updateTimeline.stop();
        }

        MainMenuWindow mainMenu = new MainMenuWindow(stage, currentUser);
        mainMenu.show();
    }
}