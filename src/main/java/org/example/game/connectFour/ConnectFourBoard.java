package org.example.game.connectFour;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.authentication.UserProfile;

import java.io.File;

public class ConnectFourBoard extends VBox{

    private final AudioClip connectFourSoundBlue;
    private final AudioClip connectFourSoundRed;
    private int connectFourSoundCounter = 0;

    public ConnectFourBoard(ConnectFourGame game, Stage stage, UserProfile currentUser) {

        // Get the project root directory
        String projectDir = System.getProperty("user.dir");

        // Build the full file path to the sound file
        String connectFourBluePath = new File(projectDir, "resources/sounds/connectFourBlue.mp3").toURI().toString();

        // Build the full file path to the sound file
        String connectFourRedPath = new File(projectDir, "resources/sounds/connectFourRed.mp3").toURI().toString();

        connectFourSoundBlue = new AudioClip(connectFourBluePath);

        connectFourSoundRed = new AudioClip(connectFourRedPath);
        
        setupConnectFourBoard();

    }
    
    private void setupConnectFourBoard() {
        VBox boardContainer = new VBox(20);
        boardContainer.setAlignment(Pos.CENTER);

        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(5);
        board.setVgap(5);

        // Create the 7x6 grid (7 columns, 6 rows)
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(60, 60);
                cell.setStyle("-fx-background-color: #3498db; -fx-background-radius: 30;");

                Region innerCircle = new Region();
                innerCircle.setPrefSize(50, 50);
                innerCircle.setStyle("-fx-background-color: #1a2530; -fx-background-radius: 25;");

                cell.getChildren().add(innerCircle);
                board.add(cell, col, row);
            }
        }

        // Create column buttons for dropping pieces
        HBox columnButtons = new HBox(5);
        columnButtons.setAlignment(Pos.CENTER);

        for (int col = 0; col < 7; col++) {
            Button dropButton = getButton(col);
            columnButtons.getChildren().add(dropButton);
        }

        boardContainer.getChildren().addAll(columnButtons, board);
        this.getChildren().add(boardContainer);
    }

    private Button getButton(int col) {
        Button dropButton = new Button("Drop");
        dropButton.setPrefWidth(60);
        dropButton.setUserData(col);
        dropButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");

        final int column = col;
        dropButton.setOnAction(e -> {
            connectFourSoundCounter++;
            if(connectFourSoundCounter % 2 == 0){
                connectFourSoundBlue.play();
            }else{
                connectFourSoundRed.play();
            }
            makeConnectFourMove(column);
        });
        return dropButton;
    }

    private void makeConnectFourMove(int column) {
        System.out.println("Dropping piece in column: " + column);
        // This would call the actual game logic in a real implementation
        simulateOpponentTurn();
    }

    private void simulateOpponentTurn() {
        // For demo purposes, simulate the opponent's turn
        Label turnLabel = new Label();
        turnLabel.setText("Opponent's Turn");
        turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");

        // After 2 seconds, switch back to player's turn
        Timeline opponentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> {
                    turnLabel.setText("Your Turn");
                    turnLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                })
        );
        opponentTimeline.play();
    }
}
