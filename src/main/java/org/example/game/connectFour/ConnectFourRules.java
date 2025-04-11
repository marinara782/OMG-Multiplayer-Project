package org.example.game.connectFour;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Controller class for the Connect Four Rules window.
 * Handles tab resizing and returning to the main menu.
 */
public class ConnectFourRules {
    public TabPane rulesTabPane;
    private Stage stage;

    /**
     * Sets the stage (window) for this controller.
     *
     * @param stage The stage to control (typically passed in from the main menu).
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the current stage, returning to the main menu.
     * This is typically triggered by a "Back" button.
     */
    public void backToMainMenu() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Initializes the rules window.
     * Called automatically when the FXML is loaded.
     * Sets up listeners to adjust tab widths dynamically as the window is resized.
     */
    public void initialize() {
        updateTabWidths();
        rulesTabPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateTabWidths();
        });
    }

    /**
     * Dynamically adjusts the width of each tab based on the total width of the TabPane.
     * Ensures each tab has at least 100px width for readability.
     */
    private void updateTabWidths() {
        double tabWidth = rulesTabPane.getWidth() / rulesTabPane.getTabs().size();
        for (Tab tab : rulesTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " + Math.max(tabWidth, 100) + "px;");
        }
    }
}
