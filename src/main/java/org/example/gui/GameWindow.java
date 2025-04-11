package org.example.gui;

import java.util.ArrayList;
import java.util.List;

import org.example.authentication.UserProfile;
import org.example.game.checkers.CheckersBoard;
import org.example.game.checkers.CheckersGame;
import org.example.game.connectFour.ConnectFourBoard;
import org.example.game.connectFour.ConnectFourGame;
import org.example.game.ticTacToe.TicTacToeGame;
import org.example.networking.Client;
import org.example.networking.GameSession;
import org.example.utilities.ChatManager;
import org.example.utilities.GameTimer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * GameWindow is the main GUI class for rendering a playable game screen.
 * It supports multiple games (Tic-Tac-Toe, Connect Four, Checkers), handles UI layout,
 * and manages game state, timer, chat interface, and user interactions.
 *
 * This class initializes:
 * - Game board and controls
 * - Game session (for multiplayer)
 * - Turn management and status bar
 * - Chat UI (with simple bot for Tic-Tac-Toe)
 * - Timed updates for real-time feedback
 *
 * It dynamically adapts its layout based on the selected game type.
 */

public class GameWindow {
    private Stage stage;
    private Scene scene;
    private BorderPane mainLayout;
    private UserProfile currentUser;
    private Object gameInstance;
    private GameSession gameSession;
    private ChatManager chatManager;
    private Client client;
    private boolean isMultiplayer;
    private GameTimer gameTimer;
    private Timeline updateTimeline;
    private VBox gameBoard;
    private Label turnLabel;
    private Label timerLabel;

    //connectFour
    private ConnectFourGame connectFourGame;

    //Tic Tac Toe game object
    private TicTacToeGame ticTacToeGame;

    /**
     * Constructs a GameWindow which displays the game board and manages gameplay logic and UI.
     *
     * @param stage         the JavaFX stage to render this game window on
     * @param gameInstance  the game object (TicTacToeGame, ConnectFourGame, or CheckersGame)
     * @param currentUser   the currently logged-in user playing the game
     */
    public GameWindow(Stage stage, Object gameInstance, UserProfile currentUser) {
        this.stage = stage;
        this.gameInstance = gameInstance;
        this.currentUser = currentUser;
        this.gameSession = new GameSession();
        this.gameTimer = new GameTimer();
        this.client = new Client();
        this.isMultiplayer = gameSession != null && gameSession.isMultiplayer();

        //ConnectFourGame logic
        if(gameInstance instanceof ConnectFourGame) {
            this.connectFourGame = (ConnectFourGame) gameInstance;
        }

        //Class tic tac toe object is set to the tic tac toe object given by the main menu window
        if (gameInstance instanceof TicTacToeGame) {
            this.ticTacToeGame = (TicTacToeGame) gameInstance;
        }

        initializeUI();
        setupGameBoard();
        startGameUpdates();
    }

    // method for initializing the UI
    private void initializeUI() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(15));
        mainLayout.setStyle("-fx-background-color: #2c3e50;");

        // Top section - Game info and controls
        HBox topBar = createTopBar();
        mainLayout.setTop(topBar);

        // Center section - Game board placeholder (will be set in setupGameBoard)
        gameBoard = new VBox();
        gameBoard.setAlignment(CENTER);
        gameBoard.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5;");
        mainLayout.setCenter(gameBoard);

        // Right section - Chat and player info
        VBox rightPanel = createRightPanel();
        mainLayout.setRight(rightPanel);

        // Bottom section - Game controls
        HBox bottomBar = createBottomBar();
        mainLayout.setBottom(bottomBar);

        scene = new Scene(mainLayout, 1200, 800);
        stage.setTitle(getGameTitle() + " - OMG Platform");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
    }

    /**
     * Creates the top bar of the game window with game title, turn indicator, timer, and exit button.
     * @return
     */
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
        turnLabel = new Label("Your Turn");
        turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");

        // Timer
        timerLabel = new Label("0:00");
        timerLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Return to main menu button
        Button exitButton = new Button("Exit Game");
        exitButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        exitButton.setOnAction(e -> confirmExit());

        topBar.getChildren().addAll(gameTitle, spacer, turnLabel, timerLabel, exitButton);

        return topBar;
    }

    /**
     * Creates the right panel containing opponent info, game stats, and chat.
     *
     * @return VBox containing all right panel sections.
     */
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

    /**
     * Creates the chat send button, including event logic for simulated bot response (TicTacToe).
     *
     * @param chatInput    The chat input TextField.
     * @param chatDisplay  The chat display TextArea.
     * @param gameInstance The current game instance.
     * @return A styled send Button with message handling logic.
     */
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

    /**
     * Creates the bottom bar for gameplay controls (Undo, Draw, Resign).
     *
     * @return HBox with all control buttons.
     */
    private HBox createBottomBar() {
        HBox bottomBar = new HBox(15);
        bottomBar.setPadding(new Insets(10, 5, 5, 5));
        bottomBar.setAlignment(CENTER);
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

    /**
     * Sets up the game board UI based on the game type.
     */
    private void setupGameBoard() {
        System.out.println("Setting up game board, instance: " + gameInstance);
        System.out.println("Is TicTacToe: " + (gameInstance instanceof TicTacToeGame));
        System.out.println("Is ConnectFour: " + (gameInstance instanceof ConnectFourGame));
        System.out.println("Is Checkers: " + (gameInstance instanceof CheckersGame));

        gameBoard.getChildren().clear();

        if (gameInstance instanceof TicTacToeGame) {
            System.out.println("Setting up TicTacToe board");
            playTicTacToeGame();
        } else if (gameInstance instanceof ConnectFourGame) {
            System.out.println("Setting up ConnectFour board");
            setupConnectFourBoard();
        } else if (gameInstance instanceof CheckersGame) {
            System.out.println("Setting up Checkers board");

            // Added by game logic team (Jacob Baggott)
            gameBoard.getChildren().clear();
            CheckersBoard checkersBoard = new CheckersBoard((CheckersGame) gameInstance);
            checkersBoard.setReturnToMainMenu(this::returnToMainMenu);
            checkersBoard.setStopGameUpdates(() -> updateTimeline.stop());
            gameBoard.getChildren().add(checkersBoard);

        }
    }

    //This is the method that has all the GUI and game logic for the tic tac toe game
    private void playTicTacToeGame() {
        gameTimer.startNewPhase();
        VBox boardContainer = new VBox(20);
        boardContainer.setAlignment(CENTER);

        GridPane board = new GridPane();
        board.setAlignment(CENTER);
        board.setHgap(10);
        board.setVgap(10);

        // Create the 3x3 grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cell = new Button();
                cell.setPrefSize(100, 100);
                cell.setStyle("-fx-background-color: #1a2530; -fx-text-fill: white; -fx-font-size: 36px;");

                // Store row and col in button properties for easy access in event handler
                cell.setUserData(new int[]{row, col});

                //Turn label at the start of the game based on the game mode
                if(ticTacToeGame.isPlayerAndComputer()){
                    turnLabel.setText("Your Turn");
                }
                else{
                    turnLabel.setText("X's Turn");
                }

                //When a player clicks on a box on the board
                cell.setOnAction(e -> {
                    Button clicked = (Button) e.getSource();
                    int[] position = (int[]) clicked.getUserData();
                    System.out.println("making move at " + position[0] + " " + position[1]);


                    //Get whose turn it is each time a box is clicked on
                    char currentPlayer = ticTacToeGame.getCurrentPlayer();
                    //Multiplayer Tic Tac Toe game
                    if (!ticTacToeGame.isPlayerAndComputer()) {
                        if (clicked.getText().isEmpty()) {
                            //Set the clicked box to O if its O's turn
                            if (currentPlayer == 'O') {
                                turnLabel.setText("X's Turn");
                                clicked.setText("O");
                                //Update our 2d array copy of the game board
                                ticTacToeGame.makeMove(position[0], position[1], 'O');
                                //Check on the 2d array if player O has won
                                boolean OpponentWin = ticTacToeGame.checkForWin('O');
                                boolean full = ticTacToeGame.isBoardFull();
                                //Player O wins
                                if (OpponentWin) {
                                    showGameOverDialog("Player O Wins!");
                                    disableBoard(board);
                                }
                                else if (full) {
                                    showGameOverDialog("Draw!");
                                    disableBoard(board);
                                }
                                //Switch to other player's turn
                                ticTacToeGame.isPlayerTurn();

                            }

                            if (isMultiplayer && client != null && gameSession != null) {
                                client.sendGameMove(gameSession, "Player " + currentPlayer + " moved to (" + position[0] + "," + position[1] + ")");
                            }


                            //Set the clicked box to X if its X's turn
                            if (currentPlayer == 'X') {
                                turnLabel.setText("O's Turn");
                                clicked.setText("X");
                                //Update our 2d array copy of the game board
                                ticTacToeGame.makeMove(position[0], position[1], 'X');
                                //Check on the 2d array if player X has won
                                boolean PlayerWin = ticTacToeGame.checkForWin('X');
                                boolean full = ticTacToeGame.isBoardFull();
                                //Player X wins
                                if (PlayerWin) {
                                    showGameOverDialog("Player X Wins!");
                                    disableBoard(board);
                                }
                                else if (full) {
                                    showGameOverDialog("Draw!");
                                    disableBoard(board);
                                }
                                //Switch to other player's turn
                                ticTacToeGame.isOpponentTurn();
                            }
                        }
                    }
                    //If playing against the computer
                    if (ticTacToeGame.isPlayerAndComputer()) {
                        //Set the clicked box to X if it's the player's turn
                        if (currentPlayer == 'X') {
                            if (clicked.getText().isEmpty()) {
                                clicked.setText("X");
                                turnLabel.setText("Computer's Turn");
                                //Update our 2d array copy of the game board
                                ticTacToeGame.makeMove(position[0], position[1], 'X');
                                //Check on the 2d array game board if player has won
                                boolean playerWin = ticTacToeGame.checkForWin('X');
                                boolean full = ticTacToeGame.isBoardFull();
                                //PLayer wins
                                if (playerWin) {
                                    showGameOverDialog("You Win!");
                                    disableBoard(board);
                                    return;
                                } else if (full) {
                                    showGameOverDialog("Draw!");
                                    disableBoard(board);
                                    return;
                                }
                                //Switch to computer's turn
                                ticTacToeGame.isOpponentTurn();

                                //Automatically trigger computer move after player's turn
                                //Simulate thinking of a turn
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), f -> {
                                    //Get the computer's move from the tic tac toe logic class
                                    int[] aiMove = ticTacToeGame.getAIMove();
                                    if (aiMove != null) {
                                        //Update the 2d array game board with the computer's move
                                        ticTacToeGame.makeMove(aiMove[0], aiMove[1], 'O');

                                        // Update the GUI with the computer's move
                                        for (Node node : board.getChildren()) {
                                            if (GridPane.getRowIndex(node) == aiMove[0] && GridPane.getColumnIndex(node) == aiMove[1]) {
                                                if (node instanceof Button) {
                                                    ((Button) node).setText("O");
                                                    turnLabel.setText("Your Turn");
                                                    break;
                                                }
                                            }
                                        }

                                        //Check if the computer has won on the 2d array game board
                                        boolean computerWin = ticTacToeGame.checkForWin('O');
                                        boolean boardFull = ticTacToeGame.isBoardFull();
                                        //Wait for the thinking animation of the computer to finish placing the computer's move
                                        Platform.runLater(() -> {
                                            //Computer wins
                                            if (computerWin) {
                                                showGameOverDialog("Computer Wins!");
                                                disableBoard(board);
                                            } else if (boardFull) {
                                                showGameOverDialog("Draw!");
                                                disableBoard(board);
                                            } else {
                                                //Switch to player's turn
                                                ticTacToeGame.isPlayerTurn();
                                            }
                                        });
                                    }
                                }));
                                timeline.play(); // Start the timeline

                            }
                        }
                    }
                });

                board.add(cell, col, row); // Add the button to the grid
            }
        }

        boardContainer.getChildren().add(board);
        gameBoard.getChildren().add(boardContainer);
    }

    /**
     * Disables all buttons in a Tic Tac Toe board to prevent further input.
     *
     * @param board The GridPane containing Tic Tac Toe cells.
     */
    private void disableBoard(GridPane board) {
        board.getChildren().forEach(node -> node.setDisable(true));
    }

    /**
     * Displays a "Game Over" alert and returns the user to the main menu once dismissed.
     *
     * @param message The message to show (e.g. winner or draw).
     */
    private void showGameOverDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setOnHidden(e -> returnToMainMenu());
        alert.showAndWait();
    }

    /**
     * Sets up the Connect Four board with buttons and visuals.
     */
    private void setupConnectFourBoard() {
        if (!(gameInstance instanceof ConnectFourGame)) return;

        ConnectFourGame connectFourGame = (ConnectFourGame) gameInstance;
        int rows = connectFourGame.getRows();
        int cols = connectFourGame.getColumns();

        VBox boardContainer = new VBox(20);
        boardContainer.setAlignment(CENTER);

        GridPane board = new GridPane();
        board.setAlignment(CENTER);
        board.setHgap(5);
        board.setVgap(5);

        // Create the board
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(60, 60);
                cell.setStyle("-fx-background-color: #3498db; -fx-background-radius: 30;");

                Region innerCircle = new Region();
                innerCircle.setPrefSize(50, 50);
                innerCircle.setStyle("-fx-background-color: #1a2530; -fx-background-radius: 25;");

                cell.getChildren().add(innerCircle);
                board.add(cell, col, row);
            }
        }

        HBox columnButtons = new HBox(5);
        columnButtons.setAlignment(CENTER);

        // Create column buttons
        for (int col = 0; col < cols; col++) {
            Button dropButton = new Button("Drop");
            dropButton.setPrefWidth(60);
            dropButton.setUserData(col);
            dropButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");

            final int column = col;
            dropButton.setOnAction(e -> makeConnectFourMove(column));

            // Disable button if it's the AI's turn
            dropButton.setDisable(connectFourGame.isVsComputer() && connectFourGame.getPlayer() == ConnectFourBoard.Blue);

            columnButtons.getChildren().add(dropButton);
        }

        boardContainer.getChildren().addAll(columnButtons, board);
        gameBoard.getChildren().clear();
        gameBoard.getChildren().add(boardContainer);
    }

    // Disable or enable user interaction for dropping pieces
    private void disableUserInteraction(boolean disable) {
        for (Node node : gameBoard.getChildren()) {
            if (node instanceof VBox) {
                VBox boardContainer = (VBox) node;
                HBox columnButtons = (HBox) boardContainer.getChildren().get(0);
                for (Node buttonNode : columnButtons.getChildren()) {
                    if (buttonNode instanceof Button) {
                        Button button = (Button) buttonNode;
                        button.setDisable(disable);
                    }
                }
            }
        }
    }

    /**
     * Returns the game title based on the game type.
     *
     * @return A string title (e.g., "Connect Four").
     */
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

    /**
     * Starts a timer to update the UI every second for time tracking and win condition checks.
     */
    private void startGameUpdates() {
        // Initialize timer with first phase
        gameTimer.startNewPhase();  // Start first timing phase

        updateTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateGameState())
        );
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    /**
     * Periodically updates the game timer and checks win conditions (only for Checkers).
     */
    private void updateGameState() {
        // Update timer display
        int seconds = gameTimer.getCurrentPhaseElapsedSeconds();
        int minutes = seconds / 60;
        seconds = seconds % 60;
        timerLabel.setText(String.format("%d:%02d", minutes, seconds));

        // NEW: If we're in a Checkers game, check for win condition
        /* I commented this out as this was causing a bug in the checkers game causing duplicate win windows to show up
        if (gameInstance instanceof CheckersGame) {
            CheckersGame checkersGame = (CheckersGame) gameInstance;
            if (checkersGame.checkWin()) {
                updateTimeline.stop();
                showGameOverDialog("Game Over: Checkers win detected!", true); ---> this is a duplicate dialogue box
            }
        }

         */
    }


    // Game move logic
    private void makeMove(int row, int col) {
        System.out.println("Making move at: " + row + ", " + col);
        // This would call the actual game logic in a real implementation
    }

    /**
     * Handles a player's move in Connect Four, checking for win/draw and updating UI.
     *
     * @param column The column in which the player drops their piece.
     */
    private void makeConnectFourMove(int column) {
        if (connectFourGame == null || connectFourGame.isGameOver()) return;

        // Prevent player from making a move during the AI's turn
        if (connectFourGame.isVsComputer() && connectFourGame.getPlayer() == ConnectFourBoard.Blue) return;

        int[][] board = connectFourGame.getBoard();
        int rows = connectFourGame.getRows();
        int player = connectFourGame.getPlayer();

        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][column] == ConnectFourBoard.Empty) {
                connectFourGame.makeMove(row, column);
                updateBoardUI(row, column, player);

                if (connectFourGame.checkWinnerHorizontal() || connectFourGame.checkWinnerVertical() || connectFourGame.checkWinnerDiagonal()) {
                    connectFourGame.setGameOver(true);
                    showGameOverDialog((player == ConnectFourBoard.Red ? "Player Red Wins!" : "Player Blue Wins!"), true);
                    return;
                }

                if (connectFourGame.checkDraw()) {
                    connectFourGame.setGameOver(true);
                    showGameOverDialog("Draw!", false);
                    return;
                }

                connectFourGame.switchTurn();
                updateTurnLabel();

                if (isMultiplayer && client != null && gameSession != null) {
                    client.sendGameMove(gameSession, "Player dropped piece in column " + column);
                }


                // Now it's the AI's turn — trigger the AI move
                if (connectFourGame.isVsComputer() && connectFourGame.getPlayer() == ConnectFourBoard.Blue) {
                    simulateComputerMove();
                }
                return;
            }
        }

        // Column is full
        Alert columnFullAlert = new Alert(Alert.AlertType.WARNING);
        columnFullAlert.setTitle("Invalid Move");
        columnFullAlert.setHeaderText("This Column is full");
        columnFullAlert.setContentText("Please select another column");
        columnFullAlert.showAndWait();
    }


    /**
     * Executes a computer move for Connect Four using a random selection of valid columns.
     */
    private void makeComputerMove() {
        if (connectFourGame.isGameOver()) return;  // Prevent AI move if the game is over

        int player = connectFourGame.getPlayer();

        // Delay to simulate thinking
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {
            if (connectFourGame.isGameOver()) return;  // Check again during the AI move

            int[][] board = connectFourGame.getBoard();
            int rows = connectFourGame.getRows();

            // Simple random AI to pick a valid column
            List<Integer> validCols = new ArrayList<>();
            for (int col = 0; col < connectFourGame.getColumns(); col++) {
                if (board[0][col] == ConnectFourBoard.Empty) {
                    validCols.add(col);
                }
            }

            if (validCols.isEmpty()) return;

            int randomCol = validCols.get((int)(Math.random() * validCols.size()));

            // Drop piece in that column
            for (int row = rows - 1; row >= 0 ; row--) {
                if (board[row][randomCol] == ConnectFourBoard.Empty) { // find the lowest empty row in that column
                    connectFourGame.makeMove(row, randomCol);
                    updateBoardUI(row, randomCol, player);
                    // to check for win conditions
                    if (connectFourGame.checkWinnerHorizontal() || connectFourGame.checkWinnerVertical() || connectFourGame.checkWinnerDiagonal()) {
                        showGameOverDialog((player == 1 ? "Player Red Wins!" : "Computer Wins!"), true);
                        return;
                    }
                    // draw condition
                    if (connectFourGame.checkDraw()) {
                        showGameOverDialog("Draw!", false);
                        return;
                    }

                    connectFourGame.switchTurn();
                    updateTurnLabel();
                    break;
                }
            }
        }));
        timeline.play();
    }

    /**
     * Visually updates the Connect Four board with the new piece color based on player.
     *
     * @param row    Row index of the new piece.
     * @param column Column index of the new piece.
     * @param player The player number (1 or 2).
     */
    private void updateBoardUI(int row, int column, int player) {

        GridPane boardContainer = (GridPane) ((VBox)gameBoard.getChildren().get(0)).getChildren().get(1);
        StackPane cell = (StackPane) getNodeByRowColumnIndex(row, column, boardContainer);
        Region piece = new Region();
        piece.setPrefSize(50, 50);
        if(player == 1) {
            piece.setStyle("-fx-background-color: #e74c3c; -fx-background-radius: 25; ");
        }else if (player == 2) {
            piece.setStyle("-fx-background-color: #3498db; -fx-background-radius: 25; ");
        }
        cell.getChildren().add(piece);


    }

    /**
     * Gets the StackPane node at a specific grid position on the Connect Four board.
     *
     * @param row            The row index.
     * @param column         The column index.
     * @param boardContainer The GridPane containing all board cells.
     * @return The matching Node or null if not found.
     */
    private Node getNodeByRowColumnIndex(int row, int column, GridPane boardContainer) {
        for(Node node: boardContainer.getChildren()){
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex((Node) node) == column){
                return node;
            }
        }
        return null;
    }

    /**
     * Updates the turn label based on the current player's turn (Red or Blue).
     */
    private void updateTurnLabel() {
        int currentPlayer = connectFourGame.getPlayer();
        String label = "";
        if(currentPlayer == 1){
            label = "Its Red's Turn";
            turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
        else if(currentPlayer == 2){
            label = "Its Blue's Turn";
            turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #3498db; -fx-font-weight: bold;");
        }
        turnLabel.setText(label);

    }

    /**
     * Handles simulated piece selection in Checkers (currently a placeholder).
     *
     * @param row The selected row.
     * @param col The selected column.
     */
    private void selectCheckersPiece(int row, int col) {
        System.out.println("Selected piece at: " + row + ", " + col);
        // This would handle piece selection and movement in a real implementation
    }

    /**
     * Simulates an opponent turn visually in games that support it (like Tic Tac Toe).
     */
    private void simulateOpponentTurn() {
        // For demo purposes, simulate the opponent's turn
        turnLabel.setText("Opponent's Turn");
        turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");

        // After 2 seconds, switch back to player's turn
        Timeline opponentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> {
                    turnLabel.setText("Your Turn");
                    turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                })
        );
        opponentTimeline.play();
    }

    /**
     * Shows a confirmation dialog for exiting the game early.
     */
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

    /**
     * Offers a draw to the opponent (simulation).
     */
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


    /**
     * Shows a dialog that the draw was accepted and exits to main menu.
     */
    private void showDrawAcceptedDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Draw Accepted");
        alert.setHeaderText("Draw Accepted");
        alert.setContentText("Your opponent has accepted your draw offer. The game ends in a draw.");

        alert.showAndWait().ifPresent(response -> returnToMainMenu());
    }

    /**
     * Shows a confirmation dialog to resign from the game.
     */
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

    /**
     * Shows a styled "Game Over" dialog and returns to main menu.
     *.
     */
    private void showGameOverDialog(String message, boolean isWin) {
        Platform.runLater(() -> {
            Alert alert = new Alert(isWin ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING);
            alert.setTitle("Game Over");
            alert.setHeaderText(message);

            // Show the dialog and wait for the user to press OK
            alert.showAndWait().ifPresent(response -> {
                returnToMainMenu();
            });
        });
    }

    /**
     * Returns the user to the main menu screen.
     */
    public void returnToMainMenu() {
        if (updateTimeline != null) {
            updateTimeline.stop();
        }

        MainMenuWindow mainMenu = new MainMenuWindow(stage, currentUser);
        mainMenu.show();
    }


    /**
     * Simulates a Connect Four AI move using a basic 3-step strategy:
     * 1) Try to win
     * 2) Block opponent win
     * 3) Choose random column
     */
    private void simulateComputerMove() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {
            int[][] board = connectFourGame.getBoard();
            int cols = connectFourGame.getColumns();

            // 1) Attempt immediate win
            for (int col = 0; col < cols; col++) {
                if (connectFourGame.canWinWithMove(col)) {
                    makeAIMove(col);
                    return;
                }
            }

            // 2) Block Opponent’s immediate win
            connectFourGame.switchTurn();
            for (int col = 0; col < cols; col++) {
                if (connectFourGame.canWinWithMove(col)) {
                    connectFourGame.switchTurn(); // switch back to AI
                    makeAIMove(col);
                    return;
                }
            }
            connectFourGame.switchTurn(); // switch back to AI if no block was needed

            // 3) Fallback random move
            List<Integer> validCols = new ArrayList<>();
            for (int col = 0; col < cols; col++) {
                if (board[0][col] == ConnectFourBoard.Empty) {
                    validCols.add(col);
                }
            }
            if (validCols.isEmpty()) return;

            int randomCol = validCols.get((int)(Math.random() * validCols.size()));
            makeAIMove(randomCol);
        }));
        timeline.play();
    }

    /**
     * handles AI moves by placing a piece in the column specified in the parameter.
     * @param column column where AI will drop piece
     */
    private void makeAIMove(int column) {
        if (connectFourGame == null || connectFourGame.isGameOver()) return;

        int[][] board = connectFourGame.getBoard();
        int rows = connectFourGame.getRows();
        int player = connectFourGame.getPlayer();

        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][column] == ConnectFourBoard.Empty) {
                connectFourGame.makeMove(row, column);
                updateBoardUI(row, column, player);
                // check win conditions
                if (connectFourGame.checkWinnerHorizontal() || connectFourGame.checkWinnerVertical() || connectFourGame.checkWinnerDiagonal()) {
                    connectFourGame.setGameOver(true);
                    showGameOverDialog("AI (Blue) Wins!", true);
                    return;
                }
                // check for draw
                if (connectFourGame.checkDraw()) {
                    connectFourGame.setGameOver(true);
                    showGameOverDialog("Draw!", false);
                    return;
                }

                connectFourGame.switchTurn();
                updateTurnLabel();
                return;
            }
        }

        // Fallback if column is full
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("AI Move Error");
        alert.setHeaderText("AI attempted to move in a full column.");
        alert.showAndWait();
    }

}