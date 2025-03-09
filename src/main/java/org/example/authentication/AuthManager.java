package org.example.authentication;
import javafx.stage.Stage;

public class AuthManager {
    private Stage parentStage;
    private Login loginDialog;
    private SignUp signupDialog;

    // Constructor that takes the parent stage
    public AuthManager(Stage parentStage) {
        this.parentStage = parentStage;
        initialize();
    }

    // Initialize both dialogs
    private void initialize() {
        loginDialog = new Login(parentStage);
        signupDialog = new SignUp(parentStage);
    }

    // Show the login dialog
    public void showLogin() {
        loginDialog.show();
    }

    // Show the signup dialog
    public void showSignUp() {
        signupDialog.show();
    }

    // Method to modify the Login class to include a link to signup
    public void setupLoginToSignupLink() {
        // This would require modifying the Login class to include a signup link
        // and callback functionality to switch between dialogs
    }
}