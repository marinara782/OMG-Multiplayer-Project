package org.example.authentication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
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
    private final String BG_COLOR = "#ecf0f1";

    // Constructor that takes a parent Stage
    public SignUp(Stage parentStage) {
        this.parentStage = parentStage;
        this.signupStage = new Stage();

        // Configure as a modal popup window
        signupStage.initModality(Modality.APPLICATION_MODAL);
        signupStage.initOwner(parentStage);
        signupStage.initStyle(StageStyle.TRANSPARENT); // Use TRANSPARENT for better shadow effect

        initializeComponents();
    }

    // Initialize all UI components
    private void initializeComponents() {
        // Create a border pane as the main container
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: transparent;");

        // Add a shadow effect to the entire dialog
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(15.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.2));
        borderPane.setEffect(dropShadow);

        // Create a grid pane for form layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setStyle("-fx-background-color: " + BG_COLOR + ";" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;");

        // Title text with slight shadow
        Text sceneTitle = new Text("Create Account");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        sceneTitle.setFill(Color.web(DARK_BLUE));
        DropShadow titleShadow = new DropShadow();
        titleShadow.setRadius(2.0);
        titleShadow.setOffsetX(1.0);
        titleShadow.setOffsetY(1.0);
        titleShadow.setColor(Color.rgb(0, 0, 0, 0.3));
        sceneTitle.setEffect(titleShadow);
        grid.add(sceneTitle, 0, 0, 2, 1);

        // Subtitle text
        Text subtitle = new Text("Join our community today");
        subtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        subtitle.setFill(Color.web("#7f8c8d"));
        grid.add(subtitle, 0, 1, 2, 1);

        // Username field with improved styling
        Label userLabel = new Label("Username");
        userLabel.setTextFill(Color.web(DARK_BLUE));
        userLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        grid.add(userLabel, 0, 2);

        userTextField = new TextField();
        userTextField.setPromptText("Choose a username");
        userTextField.setStyle("-fx-background-color: white;" +
                "-fx-border-color: #bdc3c7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8;" +
                "-fx-font-size: 13px;");
        userTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                userTextField.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: " + LIGHT_BLUE + ";" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8;" +
                        "-fx-font-size: 13px;");
            } else {
                userTextField.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: #bdc3c7;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8;" +
                        "-fx-font-size: 13px;");
            }
        });
        grid.add(userTextField, 0, 3, 2, 1);

        // Email field with improved styling
        Label emailLabel = new Label("Email");
        emailLabel.setTextFill(Color.web(DARK_BLUE));
        emailLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        grid.add(emailLabel, 0, 4);

        emailTextField = new TextField();
        emailTextField.setPromptText("Enter your email");
        emailTextField.setStyle("-fx-background-color: white;" +
                "-fx-border-color: #bdc3c7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8;" +
                "-fx-font-size: 13px;");
        emailTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                emailTextField.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: " + LIGHT_BLUE + ";" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8;" +
                        "-fx-font-size: 13px;");
            } else {
                emailTextField.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: #bdc3c7;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8;" +
                        "-fx-font-size: 13px;");
            }
        });
        grid.add(emailTextField, 0, 5, 2, 1);

        // Password field with improved styling
        Label passwordLabel = new Label("Password");
        passwordLabel.setTextFill(Color.web(DARK_BLUE));
        passwordLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        grid.add(passwordLabel, 0, 6);

        passwordField = new PasswordField();
        passwordField.setPromptText("Create a password");
        passwordField.setStyle("-fx-background-color: white;" +
                "-fx-border-color: #bdc3c7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8;" +
                "-fx-font-size: 13px;");
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordField.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: " + LIGHT_BLUE + ";" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8;" +
                        "-fx-font-size: 13px;");
            } else {
                passwordField.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: #bdc3c7;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8;" +
                        "-fx-font-size: 13px;");
            }
        });
        grid.add(passwordField, 0, 7, 2, 1);

        // Confirm Password field with improved styling
        Label confirmPasswordLabel = new Label("Confirm Password");
        confirmPasswordLabel.setTextFill(Color.web(DARK_BLUE));
        confirmPasswordLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        grid.add(confirmPasswordLabel, 0, 8);

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        confirmPasswordField.setStyle("-fx-background-color: white;" +
                "-fx-border-color: #bdc3c7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8;" +
                "-fx-font-size: 13px;");
        confirmPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                confirmPasswordField.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: " + LIGHT_BLUE + ";" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8;" +
                        "-fx-font-size: 13px;");
            } else {
                confirmPasswordField.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: #bdc3c7;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8;" +
                        "-fx-font-size: 13px;");
            }
        });
        grid.add(confirmPasswordField, 0, 9, 2, 1);

        // Terms of service checkbox with improved style
        CheckBox termsCheckBox = new CheckBox("I accept the Terms of Service");
        termsCheckBox.setTextFill(Color.web("#7f8c8d"));
        termsCheckBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        HBox termsBox = new HBox(termsCheckBox);
        termsBox.setAlignment(Pos.CENTER_LEFT);
        termsBox.setPadding(new Insets(5, 0, 5, 0));
        grid.add(termsBox, 0, 10, 2, 1);

        // Sign up button with improved styling
        Button signupButton = new Button("Create Account");
        signupButton.setPrefWidth(180);
        signupButton.setPrefHeight(40);
        signupButton.setStyle("-fx-background-color: " + LIGHT_BLUE + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 20;");

        // Cancel button with improved styling
        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);
        cancelButton.setPrefHeight(40);
        cancelButton.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: " + DARK_BLUE + ";" +
                "-fx-border-color: " + DARK_BLUE + ";" +
                "-fx-border-radius: 20;" +
                "-fx-background-radius: 20;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 14px;");

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(cancelButton, signupButton);
        grid.add(buttonBox, 0, 11, 2, 1);

        // Login link with improved styling
        HBox loginBox = new HBox();
        loginBox.setAlignment(Pos.CENTER);
        Text loginText = new Text("Already have an account? ");
        loginText.setFill(Color.web("#7f8c8d"));
        Hyperlink loginLink = new Hyperlink("Log in");
        loginLink.setStyle("-fx-text-fill: " + LIGHT_BLUE + ";");

        loginLink.setOnAction(e -> {
            signupStage.close();
            Login loginDialog = new Login(signupStage);
            loginDialog.show();
        });


        loginBox.getChildren().addAll(loginText, loginLink);
        grid.add(loginBox, 0, 12, 2, 1);

        // Message label to show signup results with improved styling
        messageLabel = new Label();
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(Double.MAX_VALUE);
        grid.add(messageLabel, 0, 13, 2, 1);

        // Button actions
        signupButton.setOnAction(e -> handleSignUp(termsCheckBox.isSelected()));
        cancelButton.setOnAction(e -> signupStage.close());

        // Button hover effects with subtle animations
        signupButton.setOnMouseEntered(e ->
                signupButton.setStyle("-fx-background-color: #2980b9;" + // Darker blue on hover
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);"));

        signupButton.setOnMouseExited(e ->
                signupButton.setStyle("-fx-background-color: " + LIGHT_BLUE + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 20;"));

        cancelButton.setOnMouseEntered(e ->
                cancelButton.setStyle("-fx-background-color: #f8f9fa;" + // Light background on hover
                        "-fx-text-fill: " + DARK_BLUE + ";" +
                        "-fx-border-color: " + DARK_BLUE + ";" +
                        "-fx-border-radius: 20;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14px;"));

        cancelButton.setOnMouseExited(e ->
                cancelButton.setStyle("-fx-background-color: transparent;" +
                        "-fx-text-fill: " + DARK_BLUE + ";" +
                        "-fx-border-color: " + DARK_BLUE + ";" +
                        "-fx-border-radius: 20;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14px;"));

        // Set the grid as the center content of the border pane
        borderPane.setCenter(grid);

        // Create the scene and set it on the stage
        Scene scene = new Scene(borderPane, 400, 620);
        scene.setFill(Color.TRANSPARENT); // Important for the shadow effect
        signupStage.setScene(scene);

        // Make the window draggable
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        grid.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        grid.setOnMouseDragged(event -> {
            signupStage.setX(event.getScreenX() - xOffset[0]);
            signupStage.setY(event.getScreenY() - yOffset[0]);
        });
    }

    // Display the signup popup
    public void show() {
        signupStage.show();
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

        // If you want or might want to close the dialog or redirect to another screen
        // For example, after a small delay ig??:
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