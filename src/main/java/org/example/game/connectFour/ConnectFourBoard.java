package org.example.game.connectFour;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private Label statusLabel;
    private Circle[][] circles;
    private Button[] dropButtons;

    public ConnectFourBoard(ConnectFourGame game, Stage stage, UserProfile currentUser) {
        this.game = game;

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
    }

    private void setupConnectFourBoard() {
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);

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

        // Reset button
        Button resetButton = new Button("Reset Game");
        resetButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        resetButton.setOnAction(e -> resetGame());

        // Add all components to the board
        boardContainer.getChildren().addAll(columnButtons, boardGrid);
        this.getChildren().addAll(statusLabel, boardContainer, resetButton);
    }

    private Button createDropButton(int col) {
        Button dropButton = new Button("Drop");
        dropButton.setPrefWidth(60);
        dropButton.setUserData(col);
        dropButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");

        dropButton.setOnAction(e -> makeConnectFourMove(col));
        return dropButton;
    }

    private void makeConnectFourMove(int column) {
        // Don't do anything if the game is over or the move is invalid
        if (game.isGameOver() || !game.isValidMove(column)) {
            return;
        }

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
            for (Button button : dropButtons) {
                button.setDisable(true);
            }
        } else {
            // Game continues, update whose turn it is
            if (game.getCurrentPlayer() == ConnectFourGame.PLAYER_RED) {
                statusLabel.setText("Red's Turn");
                statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            } else {
                statusLabel.setText("Blue's Turn");
                statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
            }
        }
    }

    private void resetGame() {
        // Reset the game logic
        game.resetGame();

        // Reset the board UI
        for (int row = 0; row < ConnectFourGame.ROWS; row++) {
            for (int col = 0; col < ConnectFourGame.COLUMNS; col++) {
                circles[row][col].setFill(Color.valueOf("#1a2530"));
            }
        }

        // Re-enable all drop buttons
        for (Button button : dropButtons) {
            button.setDisable(false);
        }

        // Reset status label
        statusLabel.setText("Red's Turn");
        statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
    }

    // If you need to simulate an AI opponent,
    /*
    private void simulateOpponentTurn() {
        // This would be where you implement AI logic
        // For a simple AI, you could:
        // 1. Pick a random valid column
        // 2. Make a move in that column

        if (game.isGameOver()) {
            return;
        }

        // Disable user interaction temporarily
        for (Button button : dropButtons) {
            button.setDisable(true);
        }

        // Display "thinking" message
        statusLabel.setText("Blue is thinking...");

        // Use a timeline to add a delay before the AI makes a move
        Timeline aiTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1.5), e -> {
                // Find a valid move
                int column;
                do {
                    column = (int)(Math.random() * ConnectFourGame.COLUMNS);
                } while (!game.isValidMove(column));

                // Make the move
                makeConnectFourMove(column);

                // Re-enable buttons if game is not over
                if (!game.isGameOver()) {
                    for (Button button : dropButtons) {
                        button.setDisable(false);
                    }
                }
            })
        );
        aiTimeline.play();
    }
    */
}