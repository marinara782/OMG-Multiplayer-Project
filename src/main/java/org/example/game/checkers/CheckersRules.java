package org.example.game.checkers;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


/**
 * Controller for the Checkers Rules window (typically used with FXML).
 * Manages tab layout and handles return-to-menu behavior.
 */
public class CheckersRules {
    public TabPane rulesTabPane;
    private Stage stage;

    /**
     * Sets the window stage for this rules controller.
     *
     * @param stage The stage to associate with this controller.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the current stage to return to the main menu.
     * Called when the "Back" button is pressed.
     */
    public void backToMainMenu() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Initializes the tab layout, setting up dynamic tab width adjustment.
     * Called automatically when the FXML view loads.
     */
    public void initialize() {
        updateTabWidths();
        rulesTabPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateTabWidths();
        });
    }

    /**
     * Distributes equal width to all tabs based on current TabPane width.
     * Ensures minimum width per tab for visibility.
     */
    private void updateTabWidths() {
        double tabWidth = rulesTabPane.getWidth() / rulesTabPane.getTabs().size();
        for (Tab tab : rulesTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " + Math.max(tabWidth, 100) + "px;");
        }
    }
}
