package org.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.authentication.Login;
import org.example.authentication.UserProfile;

import java.io.IOException;

public class SignUpWindowController {
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField emailField;
    public Button RegisterButton;
    public CheckBox checkBox;
    private Stage stage;

    /**
     * Setter Method for Stage
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * User imputs Username, Email, Password to be able to register
     * @param actionEvent
     */
    public void handleRegister(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        if (!email.contains("@") || !email.contains(".")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Weak Password", "Password must be at least 8 characters long.");
            return;
        }

        if (!checkBox.isSelected()) {
            showAlert(Alert.AlertType.ERROR,"Terms Not Accepted","You must agree to the Terms and Conditions.");
            return;
        }

        try {
            // Try to create the account
            boolean success = Login.createAccount(username, password, email,  "000-000-0000");
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
                UserProfile newUser = new UserProfile(); // Optionally fill this in with details
                OpenMainMenu(newUser);
            } else {
                showAlert(Alert.AlertType.ERROR, "Account Exists", "Username already exists. Please choose another one.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while creating the account.");
        }
    }

    /**
     * moves stage to the main menu
     * @param user
     */
    private void OpenMainMenu(UserProfile user) {
        try {
            Stage mainMenuStage = new Stage(); // Create a new Stage for the main menu
            MainMenuWindow mainMenu = new MainMenuWindow(mainMenuStage, user);
            mainMenuStage.show(); // Show the new main menu stage

            Stage signupStage = (Stage) RegisterButton.getScene().getWindow();
            signupStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the main menu.");
        }
    }

    /**
     * Pop alert with info
     * @param alertType
     * @param title
     * @param content
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * moves stage to back to login window
     * @param actionEvent
     */
    public void handleBackToLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow2.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Login - OMG");
            loginStage.setScene(new Scene(root));
            loginStage.show();

            Stage signupStage = (Stage) RegisterButton.getScene().getWindow();
            signupStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the Login Window.");
        }
        }
    }
