package org.example.game.ticTacToe;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Controller class for displaying the Tic-Tac-Toe game rules using tabs.
 */
public class TicTacToeRules {
    public TabPane rulesTabPane;
    private Stage stage;

    /**
     * Sets the stage so this controller can close the window when navigating back to the main menu.
     * @param stage the Stage to associate with this rules window
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the rules window and returns to the main menu.
     */
    public void backToMainMenu() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Initializes the rules tab layout and adds a listener to auto-resize tabs as the window width changes.
     */
    public void initialize() {
        updateTabWidths();
        rulesTabPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateTabWidths();
        });
    }

    /**
     * Dynamically sets the width of each tab based on the total available width.
     * Ensures readability with a minimum tab width of 100px.
     */
    private void updateTabWidths() {
        double tabWidth = rulesTabPane.getWidth() / rulesTabPane.getTabs().size();
        for (Tab tab : rulesTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " + Math.max(tabWidth, 100) + "px;");
        }
    }
}
