package org.example.game.connectFour;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

public class ConnectFourSetup {
    private final Stage primaryStage;
    private final UserProfile currentUser;

    // Game options
    private boolean playAgainstComputer = true;
    private boolean playerIsRed = true;
    private String difficulty = "Medium"; // Easy, Medium, Hard

    public ConnectFourSetup(Stage primaryStage, UserProfile currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;

        showSetupScreen();
    }

    private void showSetupScreen() {
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

            // Start the game with these settings
            startGame();
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

        // Create and show the scene
        Scene setupScene = new Scene(setupBox, 400, 500);
        primaryStage.setScene(setupScene);
        primaryStage.setTitle("Connect Four - Setup");
        primaryStage.show();
    }

    private void startGame() {
        // Create game with selected options
        ConnectFourGame game = new ConnectFourGame();
        ConnectFourBoard board = new ConnectFourBoard(game, primaryStage, currentUser, playAgainstComputer, playerIsRed, difficulty);

        // Show the game board
        Scene gameScene = new Scene(board, 600, 700);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Connect Four");
        primaryStage.centerOnScreen();
    }
}