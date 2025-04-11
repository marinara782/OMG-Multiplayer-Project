package org.example.gui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.authentication.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.io.FileNotFoundException;

public class LoginWindowController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button signupButton;
    @FXML private ImageView image1;
    @FXML private ImageView image2;
    @FXML private ImageView image3;

    private ImageView[] images;
    private int currentIndex = 0;

    private final UserDatabaseStub userDatabase = new UserDatabaseStub();
    private final Login login = new Login();
    private Stage stage;

    /**
     * setter method
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *  will check the information inputted into the text field and password field and login the user if correct info inputted
     * @param actionEvent
     */
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
            boolean authenticated = login.login_account(username, password);

            if (!usernameExists) {
                showAlert("Error", "Username not found. Please sign up.");
            } else if (!authenticated) {
                showAlert("Error", "Incorrect password. Please try again.");
            } else {
                showAlert("Success", "Login successful!");
                UserProfile authenticatedUser = new UserProfile(); // Fetch full user details if needed
                OpenMainMenu(authenticatedUser);
            }
        } catch(FileNotFoundException e) {
        showAlert("Error", "User database not found.");
        }
    }

    /**
     * moves to stage to main menu
     * @param user
     */
    private void OpenMainMenu(UserProfile user) {
        try {

            Stage loginStage = (Stage) loginButton.getScene().getWindow();

            // Now load main menu
            MainMenuWindow mainMenu = new MainMenuWindow(loginStage, user);
            mainMenu.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the main menu.");
        }
    }

    /**
     *  Once the Signup button is pressed it will close the current scene and send the user to the signup window
     * @param actionEvent
     */
    public void handleSignup(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpWindow.fxml"));
            Parent root = loader.load();
            Stage signUpStage = new Stage();
            signUpStage.setTitle("Sign Up");
            signUpStage.setScene(new Scene(root));
            signUpStage.show();

            // Close the login window using the button's stage
            Stage loginStage = (Stage) signupButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the sign-up window.");
        }
    }



    /**
     * Shows any alerts and messages to the user
     * @param title
     * @param message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * moves stage to forget password window
     * @param actionEvent
     */
    public void handleForgetPassword(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgetPasswordWindow.fxml"));
            Parent root = loader.load();
            Stage ForgetPasswordStage = new Stage();
            ForgetPasswordStage.setTitle("Sign Up");
            ForgetPasswordStage.setScene(new Scene(root));
            ForgetPasswordStage.show();

            // Close the login window using the button's stage
            Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the sign-up window.");
        }

    }

    /**
     * Creates a guest user, sends user to main menu
     * @param actionEvent
     */
    public void handleJoinGuest(ActionEvent actionEvent) {
        UserProfile currentUser = new UserProfile();
        OpenMainMenu(currentUser);
    }

    //Mainly for LoginWindow2 as extra features, can be taken out if not needed

    /**
     * Sets the Images onto UI
     */
    public void initialize() {
        images = new ImageView[]{image1, image2, image3};

        // Ensure only the first image is visible initially
        for (int i = 1; i < images.length; i++) {
            images[i].setVisible(false);
        }

        // Timeline for automatic rotation
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(8), event -> rotateImages()));
        timeline.setCycleCount(Timeline.INDEFINITE); // Loop forever
        timeline.play();
    }

    /**
     * Rotates the Images based on a timer
     */
    private void rotateImages() {
        ImageView currentImage = images[currentIndex];
        currentIndex = (currentIndex + 1) % images.length;
        ImageView nextImage = images[currentIndex];

        System.out.println("Switching to image index: " + currentIndex); // Debugging

        // Smooth fade-out of the current image
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), currentImage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            currentImage.setVisible(false); // Hide current image
            nextImage.setVisible(true); // Show next image

            // Smooth fade-in of the next image
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), nextImage);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }
}

