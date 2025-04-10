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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow2.fxml"));
        Parent root = loader.load();

        LoginWindowController controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

       // The commented code loads the mainmenuwindow directly (leaving for debugging)
//        UserProfile currentUser = new UserProfile();
//
//        SceneManager.setPrimaryStage(new Stage());
//        Create and show the MainMenuWindow with the stage and user
//        MainMenuWindow mainMenuWindow = new MainMenuWindow(stage, currentUser);
//        mainMenuWindow.show();
    }

    public static void main(String[] args) {
        launch();
    }
}