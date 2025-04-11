package org.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginWindow2.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login");
        stage.show();

/*
        UserProfile currentUser = new UserProfile();
        SceneManager.setPrimaryStage(new Stage());
        // Create and show the MainMenuWindow with the stage and user
        MainMenuWindow mainMenuWindow = new MainMenuWindow(stage, currentUser);
        mainMenuWindow.show();

 */
    }

    public static void main(String[] args) {
        launch();
    }
}