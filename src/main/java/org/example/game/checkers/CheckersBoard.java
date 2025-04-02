//package org.example.game.checkers;
//
//import javafx.application.Platform;
//import javafx.geometry.Pos;
//import javafx.scene.control.Alert;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.*;
//
//public class CheckersBoard extends VBox {
//    private final CheckersGame game;
//    private final GridPane boardGrid;
//    private int[] selected = null;
//
//    public CheckersBoard(CheckersGame game) {
//        this.game = game;
//        this.boardGrid = new GridPane();
//
//        this.setAlignment(Pos.CENTER);
//        boardGrid.setAlignment(Pos.CENTER);
//        boardGrid.setHgap(0);
//        boardGrid.setVgap(0);
//
//        this.getChildren().add(boardGrid);
//        this.setStyle("-fx-alignment: center;");
//        drawBoard();
//    }
//
//    private void drawBoard() {
//        boardGrid.getChildren().clear();
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                StackPane cell = new StackPane();
//                cell.setPrefSize(60, 60);
//                boolean isLight = (row + col) % 2 == 0;
//                cell.setStyle("-fx-background-color: " + (isLight ? "#ecf0f1" : "#34495e") + ";");
//                cell.setUserData(new int[]{row, col});
//
//                if (!isLight) {
//                    cell.setOnMouseClicked(this::handleClick);
//                }
//                int piece = game.getBoard()[row][col];
//                if (piece != 0) {
//                    Region pieceNode = new Region();
//                    pieceNode.setPrefSize(40, 40);
//                    if (piece > 0) {
//                        pieceNode.setStyle("-fx-background-color: #e74c3c; -fx-background-radius: 20;");
//                    } else {
//                        pieceNode.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
//                    }
//                    if (Math.abs(piece) == 2) {
//                        pieceNode.setStyle(pieceNode.getStyle() + " -fx-effect: dropshadow(gaussian, gold, 6, 0.3, 0, 0);");
//                    }
//                    if (selected != null && selected[0] == row && selected[1] == col) {
//                        pieceNode.setStyle(pieceNode.getStyle() + " -fx-border-color: yellow; -fx-border-width: 3;");
//                    }
//
//                    cell.getChildren().add(pieceNode);
//                }
//                boardGrid.add(cell, col, row);
//            }
//        }
//    }
//
//    private void handleClick(MouseEvent e) {
//        StackPane clicked = (StackPane) e.getSource();
//        int[] pos = (int[]) clicked.getUserData();
//        int row = pos[0], col = pos[1];
//
//        if (selected == null) {
//            if (game.isPlayersTurn() && game.getPiece(row, col) < 0) {
//                selected = pos;
//                drawBoard();
//            }
//            return;
//        }
//
//        boolean moved = game.movePiece(selected[0], selected[1], row, col);
//        selected = null;
//        drawBoard();
//        if (moved && game.checkWin()) {
//            showEndDialog("Player wins!");
//            return;
//        }
//
//        if (moved) {
//            Platform.runLater(() -> {
//                game.computerMove();
//                drawBoard();
//                if (game.checkWin()) {
//                    showEndDialog("Computer wins!");
//                }
//            });
//        }
//    }
//
//    private void showEndDialog(String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Game Over");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}
//



package org.example.game.checkers;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * CheckersBoard handles the GUI layer of the checkers game.
 * It draws the board, handles user interactions, and connects with game logic.
 */
public class CheckersBoard extends VBox {

    private final CheckersGame game;          // Backend game logic instance
    private final GridPane boardGrid;         // Grid for drawing the board
    private int[] selectedPosition = null;    // Currently selected piece (row, col)
    private List<int[]> highlightedMoves = new ArrayList<>(); // Valid moves for selected piece
    private boolean gameEnded = false;        // Flag to prevent interaction after game ends

    /**
     * Constructor to initialize the board view and setup layout.
     */
    public CheckersBoard(CheckersGame game) {
        this.game = game;
        this.boardGrid = new GridPane();

        // Set alignment and layout settings
        this.setAlignment(Pos.CENTER);
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(0);
        boardGrid.setVgap(0);

        // Add board and undo button to the view
        this.getChildren().addAll(boardGrid, createUndoButton());
        this.setStyle("-fx-alignment: center;");

        drawBoard(); // Initial board rendering
    }

    /**
     * Creates the Undo button and binds it to undo functionality.
     */
    private Button createUndoButton() {
        Button undoBtn = new Button("Undo");
        undoBtn.setOnAction(e -> {
            if (!gameEnded) {
                game.undoMove();                  // Call backend undo
                selectedPosition = null;
                highlightedMoves.clear();
                drawBoard();                      // Redraw board after undo
            }
        });
        return undoBtn;
    }

    /**
     * Draws the board and all pieces.
     * Also highlights selected and valid move positions.
     */
    private void drawBoard() {
        boardGrid.getChildren().clear();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(60, 60);

                // Light and dark tile colors
                boolean isLight = (row + col) % 2 == 0;
                String backgroundColor = isLight ? "#ecf0f1" : "#34495e";

                // Highlight if the cell is a valid move
                if (isHighlighted(row, col)) {
                    backgroundColor = "#27ae60";  // Green highlight
                }

                cell.setStyle("-fx-background-color: " + backgroundColor + ";");
                cell.setUserData(new int[]{row, col}); // Store row/col in UserData

                // Enable click only on dark squares
                if (!isLight) {
                    cell.setOnMouseClicked(this::handleClick);
                }

                // Draw piece if one exists at this cell
                int piece = game.getBoard()[row][col];
                if (piece != 0) {
                    Region pieceNode = new Region();
                    pieceNode.setPrefSize(40, 40);

                    // Red or black piece styles
                    if (piece > 0) {
                        pieceNode.setStyle("-fx-background-color: #e74c3c; -fx-background-radius: 20;");
                    } else {
                        pieceNode.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
                    }

                    // Add glow effect if it's a king
                    if (Math.abs(piece) == 2) {
                        pieceNode.setStyle(pieceNode.getStyle() + " -fx-effect: dropshadow(gaussian, gold, 6, 0.3, 0, 0);");
                    }

                    // Highlight selected piece with yellow border
                    if (selectedPosition != null && selectedPosition[0] == row && selectedPosition[1] == col) {
                        pieceNode.setStyle(pieceNode.getStyle() + " -fx-border-color: yellow; -fx-border-width: 3;");
                    }

                    cell.getChildren().add(pieceNode);
                }

                // Add cell to grid
                boardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Handles user clicks for selecting and moving pieces.
     */
    private void handleClick(MouseEvent e) {
        if (gameEnded) return;

        StackPane clicked = (StackPane) e.getSource();
        int[] pos = (int[]) clicked.getUserData();
        int row = pos[0], col = pos[1];

        // Select a piece to move
        if (selectedPosition == null) {
            if (game.isPlayersTurn() && game.getPiece(row, col) < 0) {
                selectedPosition = pos;
                highlightedMoves = game.getValidMovesFor(row, col); // Get valid moves
                drawBoard();
            }
            return;
        }

        // Attempt to move the selected piece
        boolean moved = game.movePiece(selectedPosition[0], selectedPosition[1], row, col);

        // Reset selection regardless of move success
        selectedPosition = null;
        highlightedMoves.clear();
        drawBoard();

        // Check for player win
        if (moved && game.checkWin()) {
            gameEnded = true;
            showEndDialog("Player wins!");
            return;
        }

        // Let the computer move next if valid
        if (moved) {
            Platform.runLater(() -> {
                game.computerMove();
                drawBoard();
                if (game.checkWin()) {
                    gameEnded = true;
                    showEndDialog("Computer wins!");
                }
            });
        }
    }

    /**
     * Checks if a cell is one of the valid highlighted move destinations.
     */
    private boolean isHighlighted(int row, int col) {
        for (int[] move : highlightedMoves) {
            if (move[0] == row && move[1] == col) return true;
        }
        return false;
    }

    /**
     * Shows a dialog when the game ends.
     */
    private void showEndDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
