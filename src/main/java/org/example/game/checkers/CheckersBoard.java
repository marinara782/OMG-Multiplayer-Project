package org.example.game.checkers;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

public class CheckersBoard extends VBox{

    public CheckersBoard(CheckersGame game, Stage stage, UserProfile currentUser) {
        setupCheckersBoard();
    }

    private void setupCheckersBoard() {
        VBox boardContainer = new VBox(20);
        boardContainer.setAlignment(Pos.CENTER);

        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);

        // Create the 8x8 grid
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(60, 60);

                // Alternating colors for the checkerboard
                boolean isLightSquare = (row + col) % 2 == 0;
                cell.setStyle("-fx-background-color: " + (isLightSquare ? "#ecf0f1" : "#34495e") + ";");

                // Add checkers pieces to initial positions
                if (!isLightSquare) {
                    if (row < 3) {
                        // Red pieces (opponent)
                        Region piece = new Region();
                        piece.setPrefSize(40, 40);
                        piece.setStyle("-fx-background-color: #e74c3c; -fx-background-radius: 20;");
                        cell.getChildren().add(piece);
                    } else if (row > 4) {
                        // Black pieces (player)
                        Region piece = new Region();
                        piece.setPrefSize(40, 40);
                        piece.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
                        cell.getChildren().add(piece);
                    }
                }

                // Store position for move handling
                cell.setUserData(new int[]{row, col});

                // Add click handler
                cell.setOnMouseClicked(e -> {
                    StackPane clicked = (StackPane) e.getSource();
                    int[] position = (int[]) clicked.getUserData();
                    selectCheckersPiece(position[0], position[1]);
                });

                board.add(cell, col, row);
            }
        }

        boardContainer.getChildren().add(board);
        this.getChildren().add(boardContainer);
    }

    private void selectCheckersPiece(int row, int col) {
        System.out.println("Selected piece at: " + row + ", " + col);
        // This would handle piece selection and movement in a real implementation
    }
}
