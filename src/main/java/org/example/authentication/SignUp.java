package org.example.authentication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignUp {
    private Stage parentStage;
    private Stage signupStage;
    private TextField userTextField;
    private TextField emailTextField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Label messageLabel;

    // Color constants based on your preferred palette
    private final String DARK_BLUE = "#2c3e50";
    private final String LIGHT_BLUE = "#3498db";
    private final String ACCENT_RED = "#e74c3c";

    // Constructor that takes a parent Stage
    public SignUp(Stage parentStage) {
        this.parentStage = parentStage;
        this.signupStage = new Stage();

        // Configure as a modal popup window
        signupStage.initModality(Modality.APPLICATION_MODAL);
        signupStage.initOwner(parentStage);
        signupStage.initStyle(StageStyle.UNDECORATED); // Remove default window decoration

        initializeComponents();
    }

    // Initialize all UI components
    private void initializeComponents() {
        // Create a border pane as the main container
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + DARK_BLUE + ";");

        // Create a grid pane for form layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: #b4cfd1;");

        // Title text
        Text sceneTitle = new Text("Create Account");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        sceneTitle.setFill(Color.web(DARK_BLUE));
        grid.add(sceneTitle, 0, 0, 2, 1);

        // Username label and field
        Label userLabel = new Label("Username:");
        userLabel.setTextFill(Color.web(DARK_BLUE));
        grid.add(userLabel, 0, 1);

        userTextField = new TextField();
        userTextField.setPromptText("Choose a username");
        userTextField.setStyle("-fx-border-color: " + LIGHT_BLUE + ";" +
                "-fx-border-radius: 3;" +
                "-fx-background-radius: 3;");
        grid.add(userTextField, 1, 1);

        // Email label and field
        Label emailLabel = new Label("Email:");
        emailLabel.setTextFill(Color.web(DARK_BLUE));
        grid.add(emailLabel, 0, 2);

        emailTextField = new TextField();
        emailTextField.setPromptText("Enter your email");
        emailTextField.setStyle("-fx-border-color: " + LIGHT_BLUE + ";" +
                "-fx-border-radius: 3;" +
                "-fx-background-radius: 3;");
        grid.add(emailTextField, 1, 2);

        // Password label and field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.web(DARK_BLUE));
        grid.add(passwordLabel, 0, 3);

        passwordField = new PasswordField();
        passwordField.setPromptText("Create a password");
        passwordField.setStyle("-fx-border-color: " + LIGHT_BLUE + ";" +
                "-fx-border-radius: 3;" +
                "-fx-background-radius: 3;");
        grid.add(passwordField, 1, 3);

        // Confirm Password label and field
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setTextFill(Color.web(DARK_BLUE));
        grid.add(confirmPasswordLabel, 0, 4);

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        confirmPasswordField.setStyle("-fx-border-color: " + LIGHT_BLUE + ";" +
                "-fx-border-radius: 3;" +
                "-fx-background-radius: 3;");
        grid.add(confirmPasswordField, 1, 4);

        // Terms of service checkbox
        CheckBox termsCheckBox = new CheckBox("I accept the Terms of Service");
        termsCheckBox.setTextFill(Color.web(DARK_BLUE));
        grid.add(termsCheckBox, 1, 5);

        // Sign up button
        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: " + LIGHT_BLUE + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;");

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: " + ACCENT_RED + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.getChildren().addAll(cancelButton, signupButton);
        grid.add(buttonBox, 1, 6);

        // Login link
        Hyperlink loginLink = new Hyperlink("Already have an account? Log in");
        loginLink.setTextFill(Color.web(LIGHT_BLUE));
        grid.add(loginLink, 1, 7);

        // Message label to show signup results
        messageLabel = new Label();
        grid.add(messageLabel, 0, 8, 2, 1);

        // Button actions
        signupButton.setOnAction(e -> handleSignUp(termsCheckBox.isSelected()));
        cancelButton.setOnAction(e -> signupStage.close());
        loginLink.setOnAction(e -> {
            signupStage.close();
            Login loginDialog = new Login(parentStage);
            loginDialog.show();
        });

        // Add hover effects for buttons
        signupButton.setOnMouseEntered(e ->
                signupButton.setStyle("-fx-background-color: #2980b9;" + // Darker blue on hover
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"));

        signupButton.setOnMouseExited(e ->
                signupButton.setStyle("-fx-background-color: " + LIGHT_BLUE + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"));

        cancelButton.setOnMouseEntered(e ->
                cancelButton.setStyle("-fx-background-color: #c0392b;" + // Darker red on hover
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"));

        cancelButton.setOnMouseExited(e ->
                cancelButton.setStyle("-fx-background-color: " + ACCENT_RED + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"));

        // Set the grid as the center content of the border pane
        borderPane.setCenter(grid);

        // Add a header bar with the app title
        HBox headerBar = new HBox();
        headerBar.setStyle("-fx-background-color: " + LIGHT_BLUE + ";");
        headerBar.setPadding(new Insets(10, 10, 10, 10));

        Text appTitle = new Text("Authentication");
        appTitle.setFill(Color.WHITE);
        appTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));

        headerBar.getChildren().add(appTitle);
        borderPane.setTop(headerBar);

        // Create the scene and set it on the stage
        Scene scene = new Scene(borderPane, 400, 450);
        signupStage.setScene(scene);

        // Make the window draggable since we're using UNDECORATED style
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        headerBar.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        headerBar.setOnMouseDragged(event -> {
            signupStage.setX(event.getScreenX() - xOffset[0]);
            signupStage.setY(event.getScreenY() - yOffset[0]);
        });
    }

    // Display the signup popup
    public void show() {
        signupStage.showAndWait();
    }

    // Handle signup button click
    private void handleSignUp(boolean termsAccepted) {
        String username = userTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("All fields are required");
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";");
            return;
        }

        // Validate email format using simple regex
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            messageLabel.setText("Please enter a valid email address");
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";");
            return;
        }

        // Check password length
        if (password.length() < 6) {
            messageLabel.setText("Password must be at least 6 characters");
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";");
            return;
        }

        // Check password match
        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match");
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";");
            return;
        }

        // Check terms acceptance
        if (!termsAccepted) {
            messageLabel.setText("You must accept the Terms of Service");
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";");
            return;
        }

        // In a real app, you would save the user info to a database
        messageLabel.setText("Account created successfully!");
        messageLabel.setStyle("-fx-text-fill: #27ae60;"); // Green for success

        // You might want to close the dialog or redirect to another screen
        // For example, after a small delay:
        // new java.util.Timer().schedule(
        //     new java.util.TimerTask() {
        //         @Override
        //         public void run() {
        //             javafx.application.Platform.runLater(() -> signupStage.close());
        //         }
        //     },
        //     2000 // close after 2 seconds
        // );
    }
}