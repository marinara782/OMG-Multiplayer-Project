package org.example.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.authentication.Login;
import org.example.authentication.UserProfile;
import javafx.event.ActionEvent;

public class LoginWindowController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button signupButton;
    //@FXML private Button guestButton; Might add in Later


    public void handleLogin(ActionEvent actionEvent) {
    }

    public void handleSignup(ActionEvent actionEvent) {
    }

//    public void guestButton(ActionEvent actionEvent) {}

}
