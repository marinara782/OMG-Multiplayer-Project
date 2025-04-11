package org.example.game.connectFour;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ConnectFourRules {
    public TabPane rulesTabPane;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void backToMainMenu() {
        if (stage != null) {
            stage.close();
        }
    }

    public void initialize() {
        updateTabWidths();
        rulesTabPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateTabWidths();
        });
    }

    private void updateTabWidths() {
        double tabWidth = rulesTabPane.getWidth() / rulesTabPane.getTabs().size();
        for (Tab tab : rulesTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " + Math.max(tabWidth, 100) + "px;");
        }
    }
}
