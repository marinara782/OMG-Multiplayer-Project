package org.example.game.checkers;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class CheckersBoard extends VBox {
    private final CheckersGame game;
    private final GridPane boardGrid;
    private int[] selected = null;

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
                int piece = game.getBoard()[row][col];
                if (piece != 0) {
                    Region pieceNode = new Region();
                    pieceNode.setPrefSize(40, 40);
                    if (piece > 0) {
                        pieceNode.setStyle("-fx-background-color: #e74c3c; -fx-background-radius: 20;");
                    } else {
                        pieceNode.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
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

    private void handleClick(MouseEvent e) {
        StackPane clicked = (StackPane) e.getSource();
        int[] pos = (int[]) clicked.getUserData();
        int row = pos[0], col = pos[1];

        if (selected == null) {
            if (game.isPlayersTurn() && game.getPiece(row, col) < 0) {
                selected = pos;
                drawBoard();
            }
            return;
        }

        boolean moved = game.movePiece(selected[0], selected[1], row, col);
        selected = null;
        drawBoard();
        if (moved && game.checkWin()) {
            showEndDialog("Player wins!");
            return;
        }

        if (moved) {
            Platform.runLater(() -> {
                game.computerMove();
                drawBoard();
                if (game.checkWin()) {
                    showEndDialog("Computer wins!");
                }
            });
        }
    }

    private void showEndDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

