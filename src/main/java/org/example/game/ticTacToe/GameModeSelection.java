package org.example.game.ticTacToe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameModeSelection {
    private GameMode selectedMode;
    private Stage dialogStage;

    // Enum to represent different game modes
    public enum GameMode {
        HUMAN_VS_HUMAN,
        HUMAN_VS_COMPUTER
    }

    public GameMode showAndWait() {
        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setTitle("Choose Game Mode");

        VBox dialogVBox = new VBox(20);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setStyle("-fx-background-color: #1a2530;");

        Label titleLabel = new Label("Choose Game Mode");
        titleLabel.setStyle("-fx-text-fill: white;");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        HBox modeBox = new HBox(20);
        modeBox.setAlignment(Pos.CENTER);

        Button humanVsHumanButton = createModeButton("Two Players");
        Button humanVsComputerButton = createModeButton("vs Computer");

        modeBox.getChildren().addAll(humanVsHumanButton, humanVsComputerButton);

        dialogVBox.getChildren().addAll(titleLabel, modeBox);

        Scene dialogScene = new Scene(dialogVBox, 400, 200);
        dialogStage.setScene(dialogScene);

        // Make the dialog modal and wait for user selection
        dialogStage.showAndWait();

        System.out.println("Selected Game Mode: " + selectedMode);
        return selectedMode;
    }

    private Button createModeButton(String mode) {
        Button button = new Button(mode);
        button.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-min-width: 150px;" +
                        "-fx-min-height: 80px;" +
                        "-fx-background-radius: 10px;"
        );

        button.setOnAction(e -> {
            // Set the game mode based on the button text
            selectedMode = mode.equals("Two Players") ?
                    GameMode.HUMAN_VS_HUMAN :
                    GameMode.HUMAN_VS_COMPUTER;
            dialogStage.close();
        });

        // Add hover effects
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #34495e;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-min-width: 150px;" +
                        "-fx-min-height: 80px;" +
                        "-fx-background-radius: 10px;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-min-width: 150px;" +
                        "-fx-min-height: 80px;" +
                        "-fx-background-radius: 10px;"
        ));

        return button;
    }
}