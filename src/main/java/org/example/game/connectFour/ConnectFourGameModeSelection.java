package org.example.game.connectFour;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.authentication.UserProfile;

public class ConnectFourGameModeSelection {
    private final Stage primaryStage;
    private final UserProfile currentUser;
    private Stage dialogStage;

    // Game options
    public boolean playAgainstComputer = true;
    public boolean playerIsRed = true;
    public String difficulty = "Medium"; // Easy, Medium, Hard

    // Colors
    private final String PRIMARY_COLOR = "#ecf0f1";
    private final String ACCENT_COLOR = "#3498db";
    private final String RED_COLOR = "#e74c3c";
    private final String BLUE_COLOR = "#3498db";
    private final String GREEN_COLOR = "#2ecc71";
    private final String DARK_TEXT = "#2c3e50";
    private final String LIGHT_TEXT = "#7f8c8d";

    public ConnectFourGameModeSelection(Stage primaryStage, UserProfile currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;

        showSetupDialog();
    }

    private void showSetupDialog() {
        // Create a new dialog stage
        dialogStage = new Stage();
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle("Connect Four - Game Setup");
        dialogStage.initStyle(StageStyle.DECORATED);

        // Main container with background color and border
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + PRIMARY_COLOR + ";");

        // Header section
        VBox headerBox = new VBox(5);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(20, 20, 10, 20));
        headerBox.setStyle("-fx-background-color: " + ACCENT_COLOR + "; -fx-background-radius: 0 0 15 15;");

        Label titleLabel = new Label("CONNECT FOUR");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitleLabel = new Label("GAME SETUP");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-opacity: 0.9;");

        headerBox.getChildren().addAll(titleLabel, subtitleLabel);
        borderPane.setTop(headerBox);

        // Content section
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPadding(new Insets(25, 30, 25, 30));

        // Create styled sections
        VBox opponentSection = createSection("SELECT YOUR OPPONENT");
        VBox colorSection = createSection("CHOOSE YOUR COLOR");
        VBox difficultySection = createSection("SET AI DIFFICULTY");

        // Opponent selection with custom radio buttons
        ToggleGroup opponentGroup = new ToggleGroup();

        HBox opponentOptions = new HBox(30);
        opponentOptions.setAlignment(Pos.CENTER);

        VBox computerOption = createOptionBox("Computer", "AI opponent with adjustable difficulty", opponentGroup, true);
        VBox humanOption = createOptionBox("Human Player", "Play against a friend on the same device", opponentGroup, false);

        opponentOptions.getChildren().addAll(computerOption, humanOption);
        opponentSection.getChildren().add(opponentOptions);

        // Color selection with visual indicators
        ToggleGroup colorGroup = new ToggleGroup();

        HBox colorOptions = new HBox(30);
        colorOptions.setAlignment(Pos.CENTER);

        VBox redOption = createColorOption("Red", "Goes First", colorGroup, true, RED_COLOR);
        VBox blueOption = createColorOption("Blue", "Goes Second", colorGroup, false, BLUE_COLOR);

        colorOptions.getChildren().addAll(redOption, blueOption);
        colorSection.getChildren().add(colorOptions);

        // Difficulty selection with styled combo box
        HBox difficultyBox = new HBox(15);
        difficultyBox.setAlignment(Pos.CENTER);

        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Medium");
        difficultyComboBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5; " +
                        "-fx-border-color: #bdc3c7; " +
                        "-fx-border-width: 1; " +
                        "-fx-padding: 5 10;"
        );
        difficultyComboBox.setPrefWidth(150);

        Label difficultyDescription = new Label("How smart should the AI be?");
        difficultyDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: " + LIGHT_TEXT + ";");

        VBox difficultyInfo = new VBox(5);
        difficultyInfo.setAlignment(Pos.CENTER_LEFT);
        difficultyInfo.getChildren().addAll(difficultyComboBox, difficultyDescription);

        difficultyBox.getChildren().add(difficultyInfo);
        difficultySection.getChildren().add(difficultyBox);

        // Extract radio buttons for later use
        RadioButton computerRadio = (RadioButton) ((VBox)computerOption).getChildren().get(0);
        RadioButton humanRadio = (RadioButton) ((VBox)humanOption).getChildren().get(0);
        RadioButton redRadio = (RadioButton) ((VBox)redOption).getChildren().get(1);
        RadioButton blueRadio = (RadioButton) ((VBox)blueOption).getChildren().get(1);

        // Control opponent type visibility
        computerRadio.setOnAction(e -> difficultySection.setVisible(true));
        humanRadio.setOnAction(e -> difficultySection.setVisible(false));

        // Button container with two buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        // Cancel button
        Button cancelButton = new Button("CANCEL");
        cancelButton.setStyle(
                "-fx-background-color: #95a5a6; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 12 20; " +
                        "-fx-background-radius: 30; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 2); " +
                        "-fx-cursor: hand;"
        );
        cancelButton.setPrefWidth(120);

        // Cancel button hover effects
        cancelButton.setOnMouseEntered(e ->
                cancelButton.setStyle(cancelButton.getStyle().replace("#95a5a6", "#7f8c8d"))
        );
        cancelButton.setOnMouseExited(e ->
                cancelButton.setStyle(cancelButton.getStyle().replace("#7f8c8d", "#95a5a6"))
        );

        // Cancel action - just close the dialog without saving options
        cancelButton.setOnAction(e -> dialogStage.close());

        // Start game button with enhanced styling and animated effects
        Button startButton = new Button("START GAME");
        startButton.setStyle(
                "-fx-background-color: " + GREEN_COLOR + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 15 30; " +
                        "-fx-background-radius: 30; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3); " +
                        "-fx-cursor: hand;"
        );
        startButton.setPrefWidth(200);

        // Button hover effect
        startButton.setOnMouseEntered(e ->
                startButton.setStyle(startButton.getStyle().replace(GREEN_COLOR, "#27ae60"))
        );
        startButton.setOnMouseExited(e ->
                startButton.setStyle(startButton.getStyle().replace("#27ae60", GREEN_COLOR))
        );

        // Button press effect
        startButton.setOnMousePressed(e ->
                startButton.setStyle(startButton.getStyle().replace("5, 0, 0, 3", "2, 0, 0, 1"))
        );
        startButton.setOnMouseReleased(e ->
                startButton.setStyle(startButton.getStyle().replace("2, 0, 0, 1", "5, 0, 0, 3"))
        );

        // Start button action to save selections and close dialog
        startButton.setOnAction(e -> {
            // Save selections based on which radio buttons are selected
            playAgainstComputer = computerRadio.isSelected();
            playerIsRed = redRadio.isSelected();

            // Save difficulty only if playing against computer
            if (playAgainstComputer) {
                difficulty = difficultyComboBox.getValue();
            }

            // Close the dialog after saving options
            dialogStage.close();
        });

        // Add buttons to button container
        buttonBox.getChildren().addAll(cancelButton, startButton);

        // Add all components to the content container
        contentBox.getChildren().addAll(
                opponentSection,
                colorSection,
                difficultySection,
                buttonBox
        );

        // Add space around the buttons
        VBox.setMargin(buttonBox, new Insets(15, 0, 5, 0));

        borderPane.setCenter(contentBox);

        // Create and show the dialog with shadow border
        Scene setupScene = new Scene(borderPane, 500, 700);
        dialogStage.setScene(setupScene);
        dialogStage.setResizable(false);
        dialogStage.showAndWait(); // This makes it modal
    }

    private VBox createSection(String title) {
        Label sectionTitle = new Label(title);
        sectionTitle.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: " + DARK_TEXT + ";"
        );

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: " + ACCENT_COLOR + "; -fx-opacity: 0.5;");

        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(sectionTitle, separator);

        VBox section = new VBox(15);
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(15, 15, 20, 15));
        section.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-color: #bdc3c7; " +
                        "-fx-border-width: 0.5; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 5, 0, 0, 2);"
        );

        section.getChildren().add(titleBox);
        return section;
    }

    private VBox createOptionBox(String title, String description, ToggleGroup group, boolean selected) {
        RadioButton radioButton = new RadioButton(title);
        radioButton.setToggleGroup(group);
        radioButton.setSelected(selected);
        radioButton.setStyle(
                "-fx-font-size: 15px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: " + DARK_TEXT + ";"
        );

        Label descLabel = new Label(description);
        descLabel.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-text-fill: " + LIGHT_TEXT + ";"
        );

        VBox optionBox = new VBox(3);
        optionBox.setAlignment(Pos.CENTER);
        optionBox.getChildren().addAll(radioButton, descLabel);

        return optionBox;
    }

    private VBox createColorOption(String colorName, String description, ToggleGroup group, boolean selected, String colorCode) {
        // Color circle indicator
        Region colorCircle = new Region();
        colorCircle.setPrefSize(30, 30);
        colorCircle.setStyle(
                "-fx-background-color: " + colorCode + "; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-color: #bdc3c7; " +
                        "-fx-border-width: 0.5; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 1);"
        );

        RadioButton radioButton = new RadioButton(colorName);
        radioButton.setToggleGroup(group);
        radioButton.setSelected(selected);
        radioButton.setStyle(
                "-fx-font-size: 15px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: " + colorCode + ";"
        );

        Label descLabel = new Label(description);
        descLabel.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-text-fill: " + LIGHT_TEXT + ";"
        );

        VBox optionBox = new VBox(5);
        optionBox.setAlignment(Pos.CENTER);
        optionBox.getChildren().addAll(colorCircle, radioButton, descLabel);

        return optionBox;
    }
}