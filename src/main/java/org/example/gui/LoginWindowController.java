package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.authentication.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.io.FileNotFoundException;

public class LoginWindowController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button signupButton;
    //@FXML private Button guestButton; Might add in Later

    private final UserDatabaseStub userDatabase = new UserDatabaseStub();

    //will check the information inputted into the text field and password field and login the user if correct info inputted
    public void handleLogin(ActionEvent actionEvent) {
       String username = usernameField.getText();
        String password = passwordField.getText();


        if (username.isEmpty()) {
            showAlert("Error", "Username cannot be empty. Please fill in your username!");
            return;
        }
        if (password.isEmpty()) {
            showAlert("Error", "Password cannot be empty. Please fill in your Password!");
            return;
        }

        try {
            boolean usernameExists = userDatabase.verify_username(username);
            boolean authenticated = userDatabase.verify_account(username, password);

            if (!usernameExists) {
                showAlert("Error", "Username not found. Please sign up.");
            } else if (!authenticated) {
                showAlert("Error", "Incorrect password. Please try again.");
            } else {
                showAlert("Success", "Login successful!");
                User user = new User(username, password, "", ""); // Fetch full user details if needed
                OpenMainMenu(user);
            }
        } catch (FileNotFoundException e) {
            showAlert("Error", "User database not found.");
            e.printStackTrace();
        }
    }

    private void OpenMainMenu() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            UserProfile currentUser = new UserProfile(      );
            MainMenuWindow mainMenuWindow = new MainMenuWindow(stage, currentUser);
            mainMenuWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the main menu.");
        }
    }

    //Once the Signup button is pressed it will close the current scene and send the user to the signup window
    public void handleSignup(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpWindow.fxml"));
            Parent root = loader.load();

            Stage signUpStage = new Stage();
            signUpStage.setTitle("Sign Up");
            signUpStage.setScene(new Scene(root));
            signUpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Shows any alerts and messages to the user
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    public void guestButton(ActionEvent actionEvent) {}

}
