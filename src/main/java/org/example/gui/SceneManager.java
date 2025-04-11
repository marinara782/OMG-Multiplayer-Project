package org.example.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.game.checkers.CheckersRules;
import org.example.game.connectFour.ConnectFourRules;
import org.example.game.ticTacToe.TicTacToeRules;
import org.example.game.checkers.CheckersRules;
import org.example.game.connectFour.ConnectFourRules;
import org.example.game.ticTacToe.TicTacToeRules;


import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    /**
     * Allows to load other windows based on the FXML path, Simplifies code
     * @param fxmlPath
     */
    public static void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/org/example/gui/fxml/" + fxmlPath)
            );

            Parent root = loader.load();
            Object controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            if (controller instanceof CheckersRules) {
                ((CheckersRules)controller).setStage(dialogStage);
            } else if (controller instanceof TicTacToeRules){
                ((TicTacToeRules)controller).setStage(dialogStage);
            } else {
                ((ConnectFourRules)controller).setStage(dialogStage);
            }

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(
                    SceneManager.class.getResource("/org/example/gui/styles/userprofile.css")
            ).toExternalForm());

            dialogStage.setScene(scene);
            dialogStage.showAndWait();

        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
