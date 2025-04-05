package org.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.authentication.Login;
import org.example.authentication.UserProfile;

import java.io.IOException;

public class SignUpWindowController {
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField emailField;
    public Button RegisterButton;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"; //How an email is supposed to be inputted
        return email.matches(emailRegex);
    }

    public void handleRegister(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Info", "Please fill in all fields.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Weak Password", "Password must be at least 6 characters long.");
            return;
        }

        boolean success = Login.createAccount(username, password, email, "");

        if (!success) {
            showAlert(Alert.AlertType.ERROR, "Username Taken", "This username is already in use.");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
            UserProfile newUser = new UserProfile(username, password, email, "");
            OpenMainMenu(newUser);
        }
    }

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

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleBackToLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
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
