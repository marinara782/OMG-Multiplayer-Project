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
 * Represents the Checkers game board UI and interaction logic.
 * This class handles rendering of the board, piece movement, highlighting valid moves,
 * and triggering game end behavior for both single and multiplayer modes.
 */
public class CheckersBoard extends VBox {
    private boolean vsComputer;
    private final CheckersGame game;
    private final GridPane boardGrid;
    private int[] selected = null;
    private final List<StackPane> highlightedCells = new ArrayList<>();
    private Runnable stopGameUpdates;

    /**
     *This method is used to stop the timer once the game is over
     *
     * @param stopGameUpdates - a Runnable game instance
     */
    public void setStopGameUpdates(Runnable stopGameUpdates) {
        this.stopGameUpdates = stopGameUpdates;
    }

    /**
     * Constructs the visual board for a given Checkers game.
     * Initializes the board grid and sets up cell rendering.
     *
     * @param game the Checkers game instance containing game logic
     */
    public CheckersBoard(CheckersGame game) {
        this.game = game;
        this.boardGrid = new GridPane();

        this.setAlignment(Pos.CENTER);
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(0);
        boardGrid.setVgap(0);
        this.getChildren().add(boardGrid);
        this.setStyle("-fx-alignment: center;");
        drawBoard();
    }

    /**
     * Renders the current state of the game board.
     * This includes cells, pieces, highlights, and selection indication.
     */
    private void drawBoard() {
        boardGrid.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(60, 60);
                boolean isLight = (row + col) % 2 == 0;
                cell.setStyle("-fx-background-color: " + (isLight ? "#ecf0f1" : "#34495e") + ";");
                cell.setUserData(new int[]{row, col});

                if (!isLight) {
                    cell.setOnMouseClicked(this::handleClick);
                }

                if (!isLight && isHighlighted(row, col)) {
                    cell.setStyle("-fx-background-color: #28b817;");
                }

                int piece = game.getBoard()[row][col];
                if (piece != 0) {
                    Region pieceNode = new Region();
                    pieceNode.setPrefSize(40, 40);
                    if (piece > 0) {
                        pieceNode.setStyle("-fx-background-color: #e74c3c; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
                    } else {
                        pieceNode.setStyle("-fx-background-color: #38a7c8; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
                    }
                    if (Math.abs(piece) == 2) {
                        pieceNode.setStyle(pieceNode.getStyle() + " -fx-effect: dropshadow(gaussian, gold, 6, 0.3, 0, 0);");
                    }
                    if (selected != null && selected[0] == row && selected[1] == col) {
                        pieceNode.setStyle(pieceNode.getStyle() + " -fx-border-color: yellow; -fx-border-width: 3;");
                    }
                    cell.getChildren().add(pieceNode);
                }
                boardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Handles user click events on board cells.
     * Performs selection, move validation, actual move execution, and triggers end-of-turn logic.
     *
     * @param e the mouse click event
     */
    private void handleClick(MouseEvent e) {
        StackPane clicked = (StackPane) e.getSource();
        int[] pos = (int[]) clicked.getUserData();
        int row = pos[0], col = pos[1];

        int clickedPiece = game.getPiece(row, col);

        // Multiplayer: allow either player to select their own piece
        if (selected == null) {
            boolean isValidPlayerPiece = game.isMultiplayer()
                    ? ((game.isPlayersTurn() && clickedPiece < 0) || (!game.isPlayersTurn() && clickedPiece > 0))
                    : (game.isPlayersTurn() && clickedPiece < 0); // Single player

            if (isValidPlayerPiece) {
                selected = pos;
                highlightMoves(game.getValidMoves(row, col));
                drawBoard();
            }
            return;
        }

        boolean moved = game.movePiece(selected[0], selected[1], row, col);
        selected = null;
        clearHighlights();
        drawBoard();
        if (turnLabelUpdater != null) turnLabelUpdater.run();

        if (moved && game.checkWin()) {
            boolean playerLost = !game.hasAnyMoves(game.isPlayersTurn());

            if (game.isMultiplayer()) {
                showEndDialog("Game Over! " + (playerLost ? "Opponent wins!" : "You win!"));
            } else {
                showEndDialog(playerLost ? "Computer wins!" : "Player wins!");
            }
            return;
        }

        // If single player, now AI should move
        if (moved && !game.isMultiplayer()) {
            PauseTransition pause = new PauseTransition(Duration.millis(800));
            pause.setOnFinished(event -> {
                game.computerMove();
                drawBoard();
                if (turnLabelUpdater != null) turnLabelUpdater.run();
                if (game.checkWin()) {
                    showEndDialog("Computer wins!");
                }
            });
            pause.play();
        }

    }
    private Runnable returnToMainMenu;

    /**
     * Displays a game-over dialog with a custom message.
     * If a return-to-main-menu callback is set, it will be invoked after closing the alert.
     *
     * @param message the message to display in the dialog
     */
    private void showEndDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        //to make sure until stopGameUpdates is not null to keep run
        if(stopGameUpdates != null) {
            stopGameUpdates.run();
        }

        if (returnToMainMenu != null) {
            returnToMainMenu.run();
        }
    }

    public void setReturnToMainMenu(Runnable returnToMainMenu) {
        this.returnToMainMenu = returnToMainMenu;
    }

    /**
     * Sets a callback to run when the game ends to return the user to the main menu.
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
     * Clears all previously highlighted move cells.
     */
    private void clearHighlights() {
        highlightedCells.clear();
    }

    /**
     * Checks if the given cell coordinates are currently highlighted.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return true if highlighted, false otherwise
     */
    private boolean isHighlighted(int row, int col) {
        for (StackPane cell : highlightedCells) {
            Integer r = GridPane.getRowIndex(cell);
            Integer c = GridPane.getColumnIndex(cell);
            if (r != null && c != null && r == row && c == col) return true;
        }
        return false;
    }

    private Runnable turnLabelUpdater;

    /**
     * Sets a callback that updates the turn label (e.g., in the GameWindow).
     * This will be triggered after a successful move or AI turn.
     *
     * @param updater the Runnable that updates the label
     */
    public void setTurnLabelUpdater(Runnable updater) {
        this.turnLabelUpdater = updater;

        if (turnLabelUpdater != null) {
            turnLabelUpdater.run();
        }
    }
}
