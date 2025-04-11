package org.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    /**
     * Play the intro video upon start
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        playIntro(stage);
    }

    /**
     * Sets and plays the Intro
     * @param stage
     */
    private void playIntro(Stage stage) {
        // Loads the Mp4 Intro animation
        URL videoURL = getClass().getResource("IntroAnimation1.mp4");
        Media media = new Media(videoURL.toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        StackPane root = new StackPane(mediaView);
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        mediaPlayer.play();

        // Switch to login screen after video ends
        mediaPlayer.setOnEndOfMedia(() -> {
            try {
                showLogin(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Allow user to skip the intro by clicking on the screen
        scene.setOnMouseClicked(event -> {
            mediaPlayer.stop();
            try {
                showLogin(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Move the stage to the Login and opens the login window
     * @param stage
     * @throws IOException
     */
    private void showLogin(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow2.fxml"));
        Parent root = loader.load();

        LoginWindowController controller = loader.getController();
        controller.setStage(stage);

        Scene loginScene = new Scene(root);
        stage.setScene(loginScene);
        stage.centerOnScreen();
        stage.setTitle("Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}