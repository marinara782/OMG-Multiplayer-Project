package org.example.game.ticTacToe;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;// Add this method to your GameWindow class
//import javafx.scene.media.AudioClip;
//import javafx.util.Duration;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TicTacToeBoard {
//
//    public VBox gameBoard;
//    public Label turnLabel;
//    public Label timerLabel;
//    private final AudioClip ticTacToeSoundX;
//    private final AudioClip ticTacToeSoundO;
//    private int ticTacToeCounter;
//
//    public TicTacToeBoard(TicTacToeGame game){
//
//        // Get the project root directory
//        String projectDir = System.getProperty("user.dir");
//
//        // Build the full file path to the sound file
//        String ticTacToeSoundXPath = new File(projectDir, "resources/sounds/ticTacToeX.mp3").toURI().toString();
//
//        // Build the full file path to the sound file
//        String ticTacToeSoundOPath = new File(projectDir, "resources/sounds/ticTacToeO.mp3").toURI().toString();
//
//        // Load the sound file
//        ticTacToeSoundX = new AudioClip(ticTacToeSoundXPath);
//
//        ticTacToeSoundO = new AudioClip(ticTacToeSoundOPath);
//
//        setupTicTacToeBoard();
//
//    }
//
//    private void setupTicTacToeBoard() {
//
//        VBox boardContainer = new VBox(20);
//        boardContainer.setAlignment(Pos.CENTER);
//
//        GridPane board = new GridPane();
//        board.setAlignment(Pos.CENTER);
//        board.setHgap(10);
//        board.setVgap(10);
//
//        TicTacToeGame ticTacToeGame = (TicTacToeGame) gameInstance;
//
//        // Create the 3x3 grid
//        for (int row = 0; row < 3; row++) {
//            for (int col = 0; col < 3; col++) {
//                Button cell = new Button();
//                cell.setPrefSize(100, 100);
//                cell.setStyle("-fx-background-color: #1a2530; -fx-text-fill: white; -fx-font-size: 36px;");
//
//                final int finalRow = row;
//                final int finalCol = col;
//
//                cell.setOnAction(e -> {
//                    if (cell.getText().isEmpty() && ticTacToeGame.makeMove(finalRow, finalCol)) {
//                        updateBoardCell(cell, ticTacToeGame.getCurrentPlayer());
//                    }
//                });
//
//                board.add(cell, col, row);
//            }
//        }
//
//        boardContainer.getChildren().add(board);
//        gameBoard.getChildren().add(boardContainer);
//    }
//
//    // Game move logic
//    private void makeMove(int row, int col) {
//        System.out.println("Making move at: " + row + ", " + col);
//        // This would call the actual game logic in a real implementation
//    }
//
//    private void simulateOpponentTurn() {
//        // For demo purposes, simulate the opponent's turn
//        turnLabel.setText("Opponent's Turn");
//        turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
//
//        // After 2 seconds, switch back to player's turn
//        Timeline opponentTimeline = new Timeline(
//                new KeyFrame(Duration.seconds(2), e -> {
//                    turnLabel.setText("Your Turn");
//                    turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");
//                })
//        );
//        opponentTimeline.play();
//    }
//
//    private void updateBoardCell(Button cell, char player) {
//        // Play sound based on player
//        if (player == 'X') {
//            ticTacToeSoundX.play();
//        } else {
//            ticTacToeSoundO.play();
//        }
//
//        cell.setText(String.valueOf(player));
//
//        // Check game status after move
//        TicTacToeGame ticTacToeGame = (TicTacToeGame) gameInstance;
//        if (ticTacToeGame.checkGameStatus() != ' ') {
//            handleGameEnd(ticTacToeGame.checkGameStatus());
//        }
//    }
//
//    private void handleGameEnd(char status) {
//        String message;
//        boolean isVictory;
//
//        switch (status) {
//            case 'X':
//                message = "You won!";
//                isVictory = true;
//                break;
//            case 'O':
//                message = "Computer won!";
//                isVictory = false;
//                break;
//            case 'D':
//                message = "It's a draw!";
//                isVictory = false;
//                break;
//            default:
//                return;
//        }
//
//        showGameOverDialog(message, isVictory);
//    }
//}


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import java.io.File;

public class TicTacToeBoard extends VBox {
    private final TicTacToeGame game;
    private final Button[][] cells;
    private final AudioClip ticTacToeSoundX;
    private final AudioClip ticTacToeSoundO;

    public TicTacToeBoard(TicTacToeGame game) {
        this.game = game;
        cells = new Button[3][3];

        // Initialize sounds
        String projectDir = System.getProperty("user.dir");
        String ticTacToeSoundXPath = new File(projectDir, "resources/sounds/ticTacToeX.mp3").toURI().toString();
        String ticTacToeSoundOPath = new File(projectDir, "resources/sounds/ticTacToeO.mp3").toURI().toString();

        ticTacToeSoundX = new AudioClip(ticTacToeSoundXPath);
        ticTacToeSoundO = new AudioClip(ticTacToeSoundOPath);

        setupBoard();
    }

    private void setupBoard() {
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(10);
        board.setVgap(10);

        // Create the 3x3 grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cell = getButton(row, col);

                cells[row][col] = cell;
                board.add(cell, col, row);
            }
        }

        this.getChildren().add(board);
        setAlignment(Pos.CENTER);
    }

    private Button getButton(int row, int col) {
        Button cell = new Button();
        cell.setPrefSize(100, 100);
        cell.setStyle("-fx-background-color: #1a2530; -fx-text-fill: white; -fx-font-size: 36px;");

        final int finalRow = row;
        final int finalCol = col;

        cell.setOnAction(e -> {
            if (cell.getText().isEmpty() && game.makeMove(finalRow, finalCol)) {
                updateCell(cell, game.getCurrentPlayer());
            }
        });
        return cell;
    }

    private void updateCell(Button cell, char player) {
        // Play appropriate sound
        if (player == 'X') {
            ticTacToeSoundX.play();
        } else {
            ticTacToeSoundO.play();
        }

        cell.setText(String.valueOf(player));

        // Check game status
        char status = game.checkGameStatus();
        if (status != ' ') {
            handleGameEnd(status);
        }
    }

    private void handleGameEnd(char status) {
        // Disable all buttons
        for (Button[] row : cells) {
            for (Button cell : row) {
                cell.setDisable(true);
            }
        }

        // Show game result (you might want to implement this differently)
        switch (status) {
            case 'X':
                System.out.println("Player X wins!");
                break;
            case 'O':
                System.out.println("Player O wins!");
                break;
            case 'D':
                System.out.println("It's a draw!");
                break;
        }
    }
}

