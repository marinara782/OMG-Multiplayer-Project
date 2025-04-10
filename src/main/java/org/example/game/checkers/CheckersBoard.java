package org.example.game.checkers;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * the class represents the graphical board for the Checkers game.
 * handles the drawing of the board, the pieces, and the interaction with the user.
 */
public class CheckersBoard extends VBox {
    private final CheckersGame game;
    private final GridPane boardGrid;
    private int[] selected = null;
    private final List<StackPane> highlightedCells = new ArrayList<>();

    /**
     * Constructor for CheckersBoard which initializes the game board.
     * @param game the CheckersGame instance that manages the game logic like moves and game state.
     */
    public CheckersBoard(CheckersGame game) { 
        this.game = game;
        this.boardGrid = new GridPane();

        this.setAlignment(Pos.CENTER); // sets the alignment of the VBox to center
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(0); // sets the horizontal gap between cells to 0
        boardGrid.setVgap(0);

        this.getChildren().add(boardGrid); // adds the grid to the VBox
        this.setStyle("-fx-alignment: center;"); // sets the alignment of the VBox
        drawBoard();
    }
    /**
     * Draws the board and pieces on the grid.
     * It clears the previous board and redraws it based on the current state of the game.
     */
    private void drawBoard() {
        boardGrid.getChildren().clear();
        for (int row = 0; row < 8; row++) { // iterates through each row of the board
            for (int col = 0; col < 8; col++) { // iterates through each column of the board
                StackPane cell = new StackPane();
                cell.setPrefSize(60, 60); // sets the preferred size of each cell
                // determines if the cell is light or dark from its position
                boolean isLight = (row + col) % 2 == 0;
                cell.setStyle("-fx-background-color: " + (isLight ? "#ecf0f1" : "#34495e") + ";"); // setting background colour of the cell
                cell.setUserData(new int[]{row, col});

                if (!isLight) {
                    cell.setOnMouseClicked(this::handleClick); // mouse click even for dark cells
                }

                if (!isLight && isHighlighted(row, col)) { // check if highlighted
                    cell.setStyle("-fx-background-color: #28b817;");
                }

                int piece = game.getBoard()[row][col]; // gets the piece at the position
                if (piece != 0) { // if there is a piece, create a visual for it
                    Region pieceNode = new Region();
                    pieceNode.setPrefSize(40, 40);
                    if (piece > 0) { // if the piece is positive, it's player 1's piece
                        pieceNode.setStyle("-fx-background-color: #e74c3c; -fx-background-radius: 20;");
                    } else {
                        pieceNode.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
                    }
                    if (Math.abs(piece) == 2) { // if the piece is a king (2 or -2), add a crown effect to represent it as a king
                        pieceNode.setStyle(pieceNode.getStyle() + " -fx-effect: dropshadow(gaussian, gold, 6, 0.3, 0, 0);");
                    }
                    if (selected != null && selected[0] == row && selected[1] == col) { // highlights the selected piece
                        pieceNode.setStyle(pieceNode.getStyle() + " -fx-border-color: yellow; -fx-border-width: 3;");
                    }
                    cell.getChildren().add(pieceNode); // adds a piece visual to the current cell
                }
                boardGrid.add(cell, col, row); // adds the cell to the grid at the position (col, row)
            }
        }
    }
    /**
     * hands clicking event on the board
     * @param e mouse event that triggers the click
     */
    private void handleClick(MouseEvent e) {
        StackPane clicked = (StackPane) e.getSource(); // gets the clicked cell made by user
        int[] pos = (int[]) clicked.getUserData(); // gets the position of the clicked cell
        int row = pos[0], col = pos[1]; // gets the row and column of the clicked cell

        // if a piece is clicked, it will check if the clicked cell is a valid move for the selected piece then move it to that cell if valid
        if (selected == null) {
            if (game.isPlayersTurn() && game.getPiece(row, col) < 0) {
                selected = pos;
                highlightMoves(game.getValidMoves(row, col));
                drawBoard();
            }
            return;
        }

        boolean moved = game.movePiece(selected[0], selected[1], row, col); // move piece from the selected position to the clicked position
        selected = null;
        clearHighlights();
        drawBoard();

        // check win condition
        if (moved && game.checkWin()) {
            showEndDialog("Player wins!");
            return;
        }

        // after player moves, computer will move after short pause
        if (moved) {
            PauseTransition pause = new PauseTransition(Duration.millis(900));
            pause.setOnFinished(event -> {
                game.computerMove();
                drawBoard();
                if (game.checkWin()) {
                    showEndDialog("Computer wins!");
                }
            });
            pause.play();
        }
    }

    /**
     * a dialogue when the game ends
     * @param message the message to be displayed in the dialog
     */
    private void showEndDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * highlights the possible moves for the selected piece
     * @param moves list of possible moves for the selected piece
     */
    private void highlightMoves(List<int[]> moves) {
        clearHighlights();
        for (int[] move : moves) {
            for (Node node : boardGrid.getChildren()) {
                Integer r = GridPane.getRowIndex(node);
                Integer c = GridPane.getColumnIndex(node);
                if (r != null && c != null && r == move[0] && c == move[1] && node instanceof StackPane cell) {
                    highlightedCells.add(cell);
                }
            }
        }
    }

    /**
     * clear highlights on board
     */
    private void clearHighlights() {
        highlightedCells.clear();
    }

    /**
     * checks if cell is highlighted
     * @param row row of the cell
     * @param col column of the cell
     * @return true if highlighted, false when not
     */
    private boolean isHighlighted(int row, int col) {
        for (StackPane cell : highlightedCells) {
            Integer r = GridPane.getRowIndex(cell);
            Integer c = GridPane.getColumnIndex(cell);
            if (r != null && c != null && r == row && c == col) return true;
        }
        return false;
    }
}






