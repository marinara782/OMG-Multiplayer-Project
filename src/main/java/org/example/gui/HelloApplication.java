package org.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Shows the UI for Login, will need to fix after
        Stage loginStage = new Stage();
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
        Scene loginScene = new Scene(loginLoader.load());
        loginStage.setTitle("Login - OMG");
        loginStage.setScene(loginScene);
        loginStage.show();

        // Assuming you already have a UserProfile object (replace with actual logic)
        UserProfile currentUser = new UserProfile(); // Or get the actual user

        // Create and show the MainMenuWindow with the stage and user
        MainMenuWindow mainMenuWindow = new MainMenuWindow(stage, currentUser);
        mainMenuWindow.show();
    }

    public static void main(String[] args) {
        launch();
    }
}