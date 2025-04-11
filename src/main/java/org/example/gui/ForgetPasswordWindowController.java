package org.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.authentication.Login;


public class ForgetPasswordWindowController {
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;

    private final Login login = new Login();

    /**
     * Handles when the User forgot their password, the user can reset it.
     * @param actionEvent
     */
    public void handleForgot(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Info", "Please fill in all fields.");
            return;
        }

        if (newPassword.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Weak Password", "Password must be at least 8 characters long.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
            return;
        }

        try {
            boolean success = login.forgot_password(username, email, newPassword);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password changed successfully. Please log in with your new password.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow2.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Login");
                stage.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Username and email do not match or don't exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred. Please try again.");
        }
    }

    /**
     * Shows popup alerts with information
     * @param type
     * @param title
     * @param message
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
