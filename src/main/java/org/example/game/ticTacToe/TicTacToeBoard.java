package org.example.game.ticTacToe;

public class TicTacToeBoard {
}



//package org.example.game.ticTacToe;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;// Add this method to your GameWindow class
//import javafx.util.Duration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//private void TicTacToeBoard() {
//    TicTacToeGame ticTacToeGame = (TicTacToeGame) gameInstance;
//    VBox boardContainer = new VBox(20);
//    boardContainer.setAlignment(Pos.CENTER);
//
//    GridPane board = new GridPane();
//    board.setAlignment(Pos.CENTER);
//    board.setHgap(10);
//    board.setVgap(10);
//
//    // Create the 3x3 grid
//    for (int row = 0; row < 3; row++) {
//        for (int col = 0; col < 3; col++) {
//            final int finalRow = row;
//            final int finalCol = col;
//            Button cell = new Button();
//            cell.setPrefSize(100, 100);
//            cell.setStyle("-fx-background-color: #1a2530; -fx-text-fill: white; -fx-font-size: 36px;");
//
//            // Store row and col in button properties for easy access in event handler
//            cell.setUserData(new int[]{finalRow, finalCol});
//
//            // Initialize the cell if game already has moves
//            try {
//                char cellValue = ticTacToeGame.getBoardValue(finalRow, finalCol);
//                if (cellValue != ' ') {
//                    cell.setText(String.valueOf(cellValue));
//                }
//            } catch (Exception e) {
//                // If method doesn't exist, just use empty cells
//            }
//
//            cell.setOnAction(e -> {
//                handleTicTacToeMove(cell, finalRow, finalCol, board);
//            });
//
//            board.add(cell, col, row);
//        }
//    }
//
//    boardContainer.getChildren().add(board);
//    gameBoard.getChildren().add(boardContainer);
//}
//
//// Handle a player's move on the TicTacToe board
//private void handleTicTacToeMove(Button clickedCell, int row, int col, GridPane board) {
//    TicTacToeGame ticTacToeGame = (TicTacToeGame) gameInstance;
//
//    // Only allow moves on empty cells
//    if (!clickedCell.getText().isEmpty()) {
//        return;
//    }
//
//    // Update the game state (assuming TicTacToeGame has a makeMove method)
//    boolean moveSuccess = false;
//    try {
//        moveSuccess = ticTacToeGame.makeMove(row, col);
//    } catch (Exception e) {
//        // If the game class doesn't have this method, just continue for this demo
//        moveSuccess = true;
//    }
//
//    if (moveSuccess) {
//        // Update the UI
//        clickedCell.setText("X");  // Assuming player is X
//
//        // Check for win or draw
//        boolean playerWon = false;
//        boolean boardFull = false;
//
//        try {
//            playerWon = ticTacToeGame.checkForWin();
//            boardFull = ticTacToeGame.isBoardFull();
//        } catch (Exception e) {
//            // Methods don't exist, implement simple win check for demo
//            playerWon = checkForWin(board, "X");
//            boardFull = isBoardFull(board);
//        }
//
//        if (playerWon) {
//            showGameOverDialog("You won!", true);
//            return;
//        } else if (boardFull) {
//            showGameOverDialog("Game ended in a draw", false);
//            return;
//        }
//
//        // Switch to opponent's turn
//        turnLabel.setText("Opponent's Turn");
//        turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
//
//        // Process opponent turn (AI in this example)
//        simulateOpponentTicTacToeMove(board);
//    }
//}
//
//// Simulate the opponent's move for TicTacToe
//private void simulateOpponentTicTacToeMove(GridPane board) {
//    // Add a delay to make it feel like the opponent is thinking
//    Timeline timeline = new Timeline(
//            new KeyFrame(Duration.seconds(1.5), event -> {
//                // Find an empty cell
//                List<Button> emptyCells = new ArrayList<>();
//
//                for (Node node : board.getChildren()) {
//                    if (node instanceof Button) {
//                        Button btn = (Button) node;
//                        if (btn.getText().isEmpty()) {
//                            emptyCells.add(btn);
//                        }
//                    }
//                }
//
//                // If there are empty cells, make a move
//                if (!emptyCells.isEmpty()) {
//                    // For simple AI, just choose a random empty cell
//                    int randomIndex = (int)(Math.random() * emptyCells.size());
//                    Button selectedCell = emptyCells.get(randomIndex);
//
//                    // Make the move
//                    selectedCell.setText("O");  // Assuming opponent is O
//
//                    // Check if opponent won
//                    boolean opponentWon = checkForWin(board, "O");
//                    boolean boardFull = isBoardFull(board);
//
//                    if (opponentWon) {
//                        showGameOverDialog("Opponent won!", false);
//                        return;
//                    } else if (boardFull) {
//                        showGameOverDialog("Game ended in a draw", false);
//                        return;
//                    }
//
//                    // Switch back to player's turn
//                    turnLabel.setText("Your Turn");
//                    turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");
//                }
//            })
//    );
//
//    timeline.play();
//}
//
//// Helper method to check for a win (used if TicTacToeGame doesn't provide this)
//private boolean checkForWin(GridPane board, String symbol) {
//    // Get all buttons in a 3x3 grid structure
//    Button[][] buttons = new Button[3][3];
//
//    for (Node node : board.getChildren()) {
//        if (node instanceof Button) {
//            Button btn = (Button) node;
//            int[] pos = (int[]) btn.getUserData();
//            buttons[pos[0]][pos[1]] = btn;
//        }
//    }
//
//    // Check rows
//    for (int i = 0; i < 3; i++) {
//        if (buttons[i][0].getText().equals(symbol) &&
//                buttons[i][1].getText().equals(symbol) &&
//                buttons[i][2].getText().equals(symbol)) {
//            return true;
//        }
//    }
//
//    // Check columns
//    for (int i = 0; i < 3; i++) {
//        if (buttons[0][i].getText().equals(symbol) &&
//                buttons[1][i].getText().equals(symbol) &&
//                buttons[2][i].getText().equals(symbol)) {
//            return true;
//        }
//    }
//
//    // Check diagonals
//    if (buttons[0][0].getText().equals(symbol) &&
//            buttons[1][1].getText().equals(symbol) &&
//            buttons[2][2].getText().equals(symbol)) {
//        return true;
//    }
//
//    if (buttons[0][2].getText().equals(symbol) &&
//            buttons[1][1].getText().equals(symbol) &&
//            buttons[2][0].getText().equals(symbol)) {
//        return true;
//    }
//
//    return false;
//}
//
//// Helper method to check if the board is full
//private boolean isBoardFull(GridPane board) {
//    for (Node node : board.getChildren()) {
//        if (node instanceof Button) {
//            Button btn = (Button) node;
//            if (btn.getText().isEmpty()) {
//                return false;
//            }
//        }
//    }
//    return true;
//}
