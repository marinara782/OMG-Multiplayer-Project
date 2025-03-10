package org.example.game.connectFour;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

public class ConnectFourGameModeSelection {
    private final Stage primaryStage;
    private final UserProfile currentUser;
    private Stage dialogStage;

    // Game options
    public boolean playAgainstComputer = true;
    public boolean playerIsRed = true;
    public String difficulty = "Medium"; // Easy, Medium, Hard

    public ConnectFourGameModeSelection(Stage primaryStage, UserProfile currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;

        showSetupDialog();
    }

    private void showSetupDialog() {
        // Create a new dialog stage
        dialogStage = new Stage();
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle("Connect Four - Setup");

        VBox setupBox = new VBox(15);
        setupBox.setAlignment(Pos.CENTER);
        setupBox.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Connect Four - Game Setup");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Opponent selection
        Label opponentLabel = new Label("Choose your opponent:");
        ToggleGroup opponentGroup = new ToggleGroup();

        RadioButton computerButton = new RadioButton("Computer");
        computerButton.setToggleGroup(opponentGroup);
        computerButton.setSelected(true);

        RadioButton humanButton = new RadioButton("Human Player");
        humanButton.setToggleGroup(opponentGroup);

        // Color selection
        Label colorLabel = new Label("Choose your color:");
        ToggleGroup colorGroup = new ToggleGroup();

        RadioButton redButton = new RadioButton("Red (Goes First)");
        redButton.setToggleGroup(colorGroup);
        redButton.setSelected(true);

        RadioButton blueButton = new RadioButton("Blue");
        blueButton.setToggleGroup(colorGroup);

        // Difficulty selection (only visible when playing against computer)
        Label difficultyLabel = new Label("Computer Difficulty:");
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Medium");

        VBox difficultyBox = new VBox(5, difficultyLabel, difficultyComboBox);
        difficultyBox.setVisible(true);

        // Hide/show difficulty based on opponent selection
        computerButton.setOnAction(e -> difficultyBox.setVisible(true));
        humanButton.setOnAction(e -> difficultyBox.setVisible(false));

        // Start game button
        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        startButton.setPrefWidth(200);

        startButton.setOnAction(e -> {
            // Save selections
            playAgainstComputer = computerButton.isSelected();
            playerIsRed = redButton.isSelected();
            if (playAgainstComputer) {
                difficulty = difficultyComboBox.getValue();
            }

            // Close the dialog
            dialogStage.close();
        });

        // Add all components to the setup screen
        setupBox.getChildren().addAll(
                titleLabel,
                new Separator(),
                opponentLabel,
                computerButton,
                humanButton,
                new Separator(),
                colorLabel,
                redButton,
                blueButton,
                new Separator(),
                difficultyBox,
                startButton
        );

        // Create and show the dialog
        Scene setupScene = new Scene(setupBox, 400, 500);
        dialogStage.setScene(setupScene);
        dialogStage.showAndWait(); // This makes it modal
    }
}