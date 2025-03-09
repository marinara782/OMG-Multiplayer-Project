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

public class Login {
    private Stage parentStage;
    private Stage loginStage;
    private TextField userTextField;
    private PasswordField passwordField;
    private Label messageLabel;
    boolean loginValidity = false;

    // Color constants based on your preferred palette
    private final String DARK_BLUE = "#2c3e50";
    private final String LIGHT_BLUE = "#3498db";
    private final String ACCENT_RED = "#e74c3c";

    // Constructor that takes a parent Stage
    public Login(Stage parentStage) {
        this.parentStage = parentStage;
        this.loginStage = new Stage();

        // Configure as a modal popup window
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.initOwner(parentStage);
        loginStage.initStyle(StageStyle.UNDECORATED); // Remove default window decoration

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
        Text sceneTitle = new Text("Login");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        sceneTitle.setFill(Color.web(DARK_BLUE));
        grid.add(sceneTitle, 0, 0, 2, 1);

        // Username label and field
        Label userLabel = new Label("Username:");
        userLabel.setTextFill(Color.web(DARK_BLUE));
        grid.add(userLabel, 0, 1);

        userTextField = new TextField();
        userTextField.setPromptText("Enter your username");
        userTextField.setStyle("-fx-border-color: " + LIGHT_BLUE + ";" +
                "-fx-border-radius: 3;" +
                "-fx-background-radius: 3;");
        grid.add(userTextField, 1, 1);

        // Password label and field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.web(DARK_BLUE));
        grid.add(passwordLabel, 0, 2);

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-border-color: " + LIGHT_BLUE + ";" +
                "-fx-border-radius: 3;" +
                "-fx-background-radius: 3;");
        grid.add(passwordField, 1, 2);

        // Login button
        Button loginButton = new Button("Sign in");
        loginButton.setStyle("-fx-background-color: " + LIGHT_BLUE + ";" +
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
        buttonBox.getChildren().addAll(cancelButton, loginButton);
        grid.add(buttonBox, 1, 4);

        // Message label to show login results
        messageLabel = new Label();
        grid.add(messageLabel, 0, 6, 2, 1);

        // Button actions
        loginButton.setOnAction(e -> handleLogin());
        cancelButton.setOnAction(e -> loginStage.close());

        // Add hover effects for buttons
        loginButton.setOnMouseEntered(e ->
                loginButton.setStyle("-fx-background-color: #2980b9;" + // Darker blue on hover
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"));

        loginButton.setOnMouseExited(e ->
                loginButton.setStyle("-fx-background-color: " + LIGHT_BLUE + ";" +
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
        Scene scene = new Scene(borderPane, 350, 300);
        loginStage.setScene(scene);

        // Make the window draggable since we're using UNDECORATED style
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        headerBar.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        headerBar.setOnMouseDragged(event -> {
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
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";");
            return;
        }

        // In a real application, you would validate against a database or service
        if (username.equals("admin") && password.equals("password")) {
            messageLabel.setText("Login successful!");
            messageLabel.setStyle("-fx-text-fill: #27ae60;"); // Green for success
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
            messageLabel.setStyle("-fx-text-fill: " + ACCENT_RED + ";");
        }
    }

    public boolean checkLoginValidity(){
        return loginValidity;
    }
}