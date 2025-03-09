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

public class Login {
    private Stage parentStage;
    private Stage loginStage;
    private TextField userTextField;
    private PasswordField passwordField;
    private Label messageLabel;
    boolean loginValidity = false;
    private AuthManager authManager;

    // Color constants based on your preferred palette
    private final String DARK_BLUE = "#2c3e50";
    private final String LIGHT_BLUE = "#3498db";
    private final String ACCENT_RED = "#e74c3c";
    private final String BG_COLOR = "#ecf0f1";

    // Constructor that takes a parent Stage
    public Login(Stage parentStage) {
        this.parentStage = parentStage;
        this.loginStage = new Stage();

        // Configure as a modal popup window
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.initOwner(parentStage);
        loginStage.initStyle(StageStyle.TRANSPARENT); // Use TRANSPARENT for better shadow effect

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
        Text sceneTitle = new Text("Welcome Back");
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
        Text subtitle = new Text("Please enter your credentials");
        subtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        subtitle.setFill(Color.web("#7f8c8d"));
        grid.add(subtitle, 0, 1, 2, 1);

        // Username label and field with improved styling
        Label userLabel = new Label("Username");
        userLabel.setTextFill(Color.web(DARK_BLUE));
        userLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        grid.add(userLabel, 0, 2);

        userTextField = new TextField();
        userTextField.setPromptText("Enter your username");
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

        // Password label and field with improved styling
        Label passwordLabel = new Label("Password");
        passwordLabel.setTextFill(Color.web(DARK_BLUE));
        passwordLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        grid.add(passwordLabel, 0, 4);

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
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
        grid.add(passwordField, 0, 5, 2, 1);

        // Remember me checkbox
        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.setTextFill(Color.web("#7f8c8d"));
        grid.add(rememberMe, 0, 6);

        // Login button with improved styling
        Button loginButton = new Button("Sign in");
        loginButton.setPrefWidth(150);
        loginButton.setPrefHeight(40);
        loginButton.setStyle("-fx-background-color: " + LIGHT_BLUE + ";" +
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
        buttonBox.getChildren().addAll(cancelButton, loginButton);
        grid.add(buttonBox, 0, 7, 2, 1);

        // Add sign up link
        HBox signupBox = new HBox();
        signupBox.setAlignment(Pos.CENTER);
        Text signupText = new Text("Don't have an account? ");
        signupText.setFill(Color.web("#7f8c8d"));
        Text signupLink = new Text("Sign up");
        signupLink.setStyle("-fx-text-fill: " + LIGHT_BLUE + ";");

        // Add click handler
        signupLink.setOnMouseClicked(event -> {
            // Close the current login dialog
            loginStage.close();

            // Show signup dialog using AuthManager
            authManager.showSignUp();
        });

        signupBox.getChildren().addAll(signupText, signupLink);
        grid.add(signupBox, 0, 8, 2, 1);

        // Message label to show login results with improved styling
        messageLabel = new Label();
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(Double.MAX_VALUE);
        grid.add(messageLabel, 0, 9, 2, 1);

        // Button actions
        loginButton.setOnAction(e -> handleLogin());
        cancelButton.setOnAction(e -> loginStage.close());

        // Button hover effects with subtle animations
        loginButton.setOnMouseEntered(e ->
                loginButton.setStyle("-fx-background-color: #2980b9;" + // Darker blue on hover
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);"));

        loginButton.setOnMouseExited(e ->
                loginButton.setStyle("-fx-background-color: " + LIGHT_BLUE + ";" +
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
        Scene scene = new Scene(borderPane, 380, 500);
        scene.setFill(Color.TRANSPARENT); // Important for the shadow effect
        loginStage.setScene(scene);

        // Make the window draggable
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        grid.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        grid.setOnMouseDragged(event -> {
            loginStage.setX(event.getScreenX() - xOffset[0]);
            loginStage.setY(event.getScreenY() - yOffset[0]);
        });
    }

    // Display the login popup
    public void show() {
        loginStage.showAndWait();
    }

    // Handle login button click
    private void handleLogin() {
        String username = userTextField.getText();
        String password = passwordField.getText();

        // Check if username and password are not empty
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username or password cannot be empty");
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";" +
                    "-fx-background-color: #ffebee;" +
                    "-fx-background-radius: 4;" +
                    "-fx-padding: 5;");
            return;
        }

        // In a real application, you would validate against a database or service
        if (username.equals("admin") && password.equals("password")) {
            messageLabel.setText("Login successful!");
            messageLabel.setStyle("-fx-text-fill: #27ae60;" + // Green for success
                    "-fx-background-color: #e8f5e9;" +
                    "-fx-background-radius: 4;" +
                    "-fx-padding: 5;");
            loginValidity = true;
            checkLoginValidity();

            // Close the login window after successful login
            // In a real app, you might want to delay this with a Timeline
            loginStage.close();

            // Here you would typically redirect to the main application
            // For example:
            // MainApplication mainApp = new MainApplication(parentStage);
            // mainApp.show();
        } else {
            messageLabel.setText("Invalid username or password");
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";" +
                    "-fx-background-color: #ffebee;" +
                    "-fx-background-radius: 4;" +
                    "-fx-padding: 5;");
        }
    }

    public boolean checkLoginValidity(){
        return loginValidity;
    }

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }
}