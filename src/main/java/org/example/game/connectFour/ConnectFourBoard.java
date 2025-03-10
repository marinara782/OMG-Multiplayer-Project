package org.example.game.connectFour;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.authentication.UserProfile;
import java.io.File;

public class ConnectFourBoard extends VBox {

    private final AudioClip connectFourSoundBlue;
    private final AudioClip connectFourSoundRed;
    private final ConnectFourGame game;
    private final Stage stage;
    private final UserProfile currentUser;
    private final boolean playAgainstComputer;
    private final boolean playerIsRed;

    private Label statusLabel;
    private Circle[][] circles;
    private Button[] dropButtons;
    private Label player2Label;

    public ConnectFourBoard(ConnectFourGame game, Stage stage, UserProfile currentUser,
                            boolean playAgainstComputer, boolean playerIsRed, String difficulty) {
        this.game = game;
        this.stage = stage;
        this.currentUser = currentUser;
        this.playAgainstComputer = playAgainstComputer;
        this.playerIsRed = playerIsRed;

        // Configure the game with these settings
        game.configureGame(playAgainstComputer, playerIsRed, difficulty);

        // Get the project root directory
        String projectDir = System.getProperty("user.dir");

        // Build the full file paths to the sound files
        String connectFourBluePath = new File(projectDir, "resources/sounds/connectFourBlue.mp3").toURI().toString();
        String connectFourRedPath = new File(projectDir, "resources/sounds/connectFourRed.mp3").toURI().toString();

        // Initialize audio clips
        connectFourSoundBlue = new AudioClip(connectFourBluePath);
        connectFourSoundRed = new AudioClip(connectFourRedPath);

        // Set up the UI
        setupConnectFourBoard();

        // If computer goes first, make the first move
        if (playAgainstComputer && !game.isHumanTurn()) {
            makeComputerMove();
        }
    }

    private void setupConnectFourBoard() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        // Create header with player information
        HBox playerInfoBox = new HBox(50);
        playerInfoBox.setAlignment(Pos.CENTER);

        // Player 1 info
        VBox player1Box = new VBox(5);
        player1Box.setAlignment(Pos.CENTER);

        Circle player1Circle = new Circle(15);
        player1Circle.setFill(Color.RED);

        Label player1Label = new Label(playerIsRed ? currentUser.getUsername() :
                (playAgainstComputer ? game.getComputerName() : "Player 2"));
        player1Label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        player1Box.getChildren().addAll(player1Circle, player1Label);

        // Player 2 info
        VBox player2Box = new VBox(5);
        player2Box.setAlignment(Pos.CENTER);

        Circle player2Circle = new Circle(15);
        player2Circle.setFill(Color.BLUE);

        player2Label = new Label(playerIsRed ?
                (playAgainstComputer ? game.getComputerName() : "Player 2") :
                currentUser.getUsername());
        player2Label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        player2Box.getChildren().addAll(player2Circle, player2Label);

        playerInfoBox.getChildren().addAll(player1Box, player2Box);

        // Create status label
        statusLabel = new Label("Red's Turn");
        statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        // Create the board container
        VBox boardContainer = new VBox(10);
        boardContainer.setAlignment(Pos.CENTER);

        // Create column buttons for dropping pieces
        HBox columnButtons = new HBox(5);
        columnButtons.setAlignment(Pos.CENTER);

        dropButtons = new Button[ConnectFourGame.COLUMNS];
        for (int col = 0; col < ConnectFourGame.COLUMNS; col++) {
            Button dropButton = createDropButton(col);
            columnButtons.getChildren().add(dropButton);
            dropButtons[col] = dropButton;
        }

        // Create the grid for the board
        GridPane boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);

        // Initialize the circles array
        circles = new Circle[ConnectFourGame.ROWS][ConnectFourGame.COLUMNS];

        // Create the grid cells with empty circles
        for (int row = 0; row < ConnectFourGame.ROWS; row++) {
            for (int col = 0; col < ConnectFourGame.COLUMNS; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(60, 60);
                cell.setStyle("-fx-background-color: #3498db; -fx-background-radius: 30;");

                Circle circle = new Circle(25);
                circle.setFill(Color.valueOf("#1a2530"));
                circles[row][col] = circle;

                cell.getChildren().add(circle);
                boardGrid.add(cell, col, row);
            }
        }

        // Create buttons for game controls
        HBox controlsBox = new HBox(20);
        controlsBox.setAlignment(Pos.CENTER);

        Button resetButton = new Button("New Game");
        resetButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        resetButton.setOnAction(e -> resetGame());

        Button setupButton = new Button("Game Setup");
        setupButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        setupButton.setOnAction(e -> {
            // Go back to the setup screen
            new ConnectFourSetup(stage, currentUser);
        });

        controlsBox.getChildren().addAll(resetButton, setupButton);

        // Add all components to the board
        boardContainer.getChildren().addAll(columnButtons, boardGrid);
        this.getChildren().addAll(
                playerInfoBox,
                new Separator(),
                statusLabel,
                boardContainer,
                new Separator(),
                controlsBox
        );
    }

    private Button createDropButton(int col) {
        Button dropButton = new Button("↓");
        dropButton.setPrefWidth(60);
        dropButton.setUserData(col);
        dropButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");

        dropButton.setOnAction(e -> makePlayerMove(col));
        return dropButton;
    }

    private void makePlayerMove(int column) {
        // Check if it's the player's turn and the move is valid
        if (game.isHumanTurn() && game.isValidMove(column)) {
            makeMove(column);

            // If playing against computer and the game isn't over, make the computer's move
            if (playAgainstComputer && !game.isGameOver()) {
                makeComputerMove();
            }
        }
    }

    private void makeComputerMove() {
        // Disable buttons during computer's turn
        setButtonsEnabled(false);

        // Update status to show computer is thinking
        updateThinkingStatus();

        // Add delay to simulate computer thinking
        Timeline computerThinking = new Timeline(
                new KeyFrame(Duration.seconds(1.2), e -> {
                    // Make the computer's move
                    int column = game.makeComputerMove();
                    if (column >= 0) {
                        makeMove(column);
                    }

                    // Re-enable buttons if game continues and it's player's turn
                    if (!game.isGameOver() && game.isHumanTurn()) {
                        setButtonsEnabled(true);
                    }
                })
        );
        computerThinking.play();
    }

    private void makeMove(int column) {
        // Play the appropriate sound
        if (game.getCurrentPlayer() == ConnectFourGame.PLAYER_RED) {
            connectFourSoundRed.play();
        } else {
            connectFourSoundBlue.play();
        }

        // Make the move and get the row where the piece landed
        int row = game.makeMove(column);

        if (row >= 0) {
            // Update the UI to show the piece
            updateBoard(row, column);

            // Update the game status
            updateGameStatus();
        }
    }

    private void updateBoard(int row, int column) {
        // Get the piece that was just placed
        int piece = game.getPieceAt(row, column);

        // Update the circle color based on the piece
        Circle circle = circles[row][column];
        if (piece == ConnectFourGame.PLAYER_RED) {
            circle.setFill(Color.RED);
        } else if (piece == ConnectFourGame.PLAYER_BLUE) {
            circle.setFill(Color.BLUE);
        }
    }

    private void updateGameStatus() {
        if (game.isGameOver()) {
            // Game is over
            if (game.getWinner() == ConnectFourGame.PLAYER_RED) {
                statusLabel.setText("Red Wins!");
                statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            } else if (game.getWinner() == ConnectFourGame.PLAYER_BLUE) {
                statusLabel.setText("Blue Wins!");
                statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
            } else {
                statusLabel.setText("Draw!");
                statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
            }

            // Disable drop buttons when game is over
            setButtonsEnabled(false);
        } else {
            // Game continues, update whose turn it is
            String playerName;
            if (game.getCurrentPlayer() == ConnectFourGame.PLAYER_RED) {
                statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
                playerName = playerIsRed ? currentUser.getUsername() :
                        (playAgainstComputer ? game.getComputerName() : "Player 2");
            } else {
                statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
                playerName = playerIsRed ?
                        (playAgainstComputer ? game.getComputerName() : "Player 2") :
                        currentUser.getUsername();
            }
            statusLabel.setText(playerName + "'s Turn");
        }
    }

    private void updateThinkingStatus() {
        String computerName = game.getComputerName();
        statusLabel.setText(computerName + " is thinking...");
        statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
    }

    private void setButtonsEnabled(boolean enabled) {
        for (Button button : dropButtons) {
            button.setDisable(!enabled);
            button.setOpacity(enabled ? 1.0 : 0.5);
        }
    }

    private void resetGame() {
        // Reset the game state
        game.resetGame();

        // Clear the board UI
        for (int row = 0; row < ConnectFourGame.ROWS; row++) {
            for (int col = 0; col < ConnectFourGame.COLUMNS; col++) {
                circles[row][col].setFill(Color.valueOf("#1a2530"));
            }
        }

        // Reset the status label
        statusLabel.setText("Red's Turn");
        statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        // Enable all buttons
        setButtonsEnabled(true);

        // If computer goes first, make the first move
        if (playAgainstComputer && !game.isHumanTurn()) {
            makeComputerMove();
        }
    }

    public void highlightWinningPieces(int[][] winningPositions) {
        if (winningPositions == null) return;

        for (int[] position : winningPositions) {
            int row = position[0];
            int col = position[1];

            // Highlight the winning piece with a brighter color or animation
            Circle circle = circles[row][col];

            // Create a pulsing animation or change to a brighter color
            if (game.getPieceAt(row, col) == ConnectFourGame.PLAYER_RED) {
                circle.setStroke(Color.GOLD);
                circle.setStrokeWidth(3);
            } else if (game.getPieceAt(row, col) == ConnectFourGame.PLAYER_BLUE) {
                circle.setStroke(Color.GOLD);
                circle.setStrokeWidth(3);
            }
        }
    }

//    public void setDifficulty(String difficulty) {
//        game.setDifficulty(difficulty);
//    }
//
//    public String getDifficulty() {
//        return game.getDifficulty();
//    }

//    public void showGameStats() {
//        // This method could display game statistics
//        // Implementation for showing game stats, win percentages, etc.
//    }
}