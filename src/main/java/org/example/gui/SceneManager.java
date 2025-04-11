package org.example.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void loadScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    SceneManager.class.getResource("/org/example/gui/fxml/" + fxmlPath)
            ));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(
                    SceneManager.class.getResource("/org/example/gui/styles/userprofile.css")
            ).toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
