package org.example.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        UserProfile currentUser = new UserProfile();

        SceneManager.setPrimaryStage(new Stage());

        // Create and show the MainMenuWindow with the stage and user
        MainMenuWindow mainMenuWindow = new MainMenuWindow(stage, currentUser);
        mainMenuWindow.show();
    }

    public static void main(String[] args) {
        launch();
    }
}