package org.example.game.ticTacToe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.authentication.UserProfile;
import org.example.gui.MainMenuWindow;

import java.io.File;

public class TicTacToeBoard extends VBox {
    private final TicTacToeGame game;
    private final Button[][] cells;
    private final AudioClip ticTacToeSoundX;
    private final AudioClip ticTacToeSoundO;
    private final Stage primaryStage;
    private final UserProfile currentUser;

    public TicTacToeBoard(TicTacToeGame game, Stage primaryStage, UserProfile currentUser) {

        this.game = game;
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
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

        // Create game result dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Game Over");

        VBox dialogVBox = new VBox(20);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setStyle("-fx-background-color: #1a2530;"); // Match game board background

        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button returnToMenuButton = new Button("Return to Main Menu");
        returnToMenuButton.setStyle(
                "-fx-background-color: #4CAF50;" + // Green button
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-padding: 10px 20px;"
        );

        // Determine game result and set styling
        switch (status) {
            case 'X':
                if(game.getPlayerSymbol() == 'X'){
                    resultLabel.setText("You Win!");
                    resultLabel.setStyle("-fx-text-fill: #00FF00;"); // Bright bold green
                }else{
                    resultLabel.setText("You Lost!");
                    resultLabel.setStyle("-fx-text-fill: #8B0000;"); // Dark bold red
                }
                break;

            case 'O':
                if(game.getPlayerSymbol() == 'O'){
                    resultLabel.setText("You Win!");
                    resultLabel.setStyle("-fx-text-fill: #00FF00;"); // Bright bold green
                }else{
                    resultLabel.setText("You Lost!");
                    resultLabel.setStyle("-fx-text-fill: #8B0000;"); // Dark bold red
                }
                break;
            case 'D':
                resultLabel.setText("It's a Draw");
                resultLabel.setStyle("-fx-text-fill: #808080;"); // Light gray
                break;
        }

        // Return to main menu action
        returnToMenuButton.setOnAction(e -> {
            dialogStage.close();
            // Assuming you have a method to show the main menu
            if (primaryStage != null && currentUser != null) {
                // Create a new MainMenuWindow with the stage and current user
                new MainMenuWindow(primaryStage, currentUser);
            }
        });

        dialogVBox.getChildren().addAll(resultLabel, returnToMenuButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialogScene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        // Optional: add a subtle border and shadow to the dialog
        dialogVBox.setStyle(
                "-fx-background-color: #1a2530;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
        );

        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
}

