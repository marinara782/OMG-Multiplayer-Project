package org.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    /**
     * Sets the Stage and opens Login Window
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow2.fxml"));
        Parent root = loader.load();

        LoginWindowController controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login");
        stage.show();


    }

    /**
     * launches the UI
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}