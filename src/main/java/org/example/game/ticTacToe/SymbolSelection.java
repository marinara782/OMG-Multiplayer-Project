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

public class SymbolSelection {
    private char playerSymbol;
    private Stage dialogStage;

    public char showAndWait() {
        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setTitle("Choose Your Symbol");

        VBox dialogVBox = new VBox(20);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setStyle("-fx-background-color: #1a2530;");

        Label titleLabel = new Label("Choose Your Symbol");
        titleLabel.setStyle("-fx-text-fill: white;");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        HBox symbolBox = new HBox(20);
        symbolBox.setAlignment(Pos.CENTER);

        Button xButton = createSymbolButton("X");
        Button oButton = createSymbolButton("O");

        symbolBox.getChildren().addAll(xButton, oButton);

        dialogVBox.getChildren().addAll(titleLabel, symbolBox);

        Scene dialogScene = new Scene(dialogVBox, 400, 200);
        dialogStage.setScene(dialogScene);

        // Make the dialog modal and wait for user selection
        dialogStage.showAndWait();

        System.out.println("Player Symbol is : " + playerSymbol);
        return playerSymbol;
    }

    private Button createSymbolButton(String symbol) {
        Button button = new Button(symbol);
        button.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 48px;" +
                        "-fx-min-width: 100px;" +
                        "-fx-min-height: 100px;" +
                        "-fx-background-radius: 10px;"
        );

        button.setOnAction(e -> {
            playerSymbol = symbol.charAt(0);
            dialogStage.close();
        });

        return button;
    }
}
