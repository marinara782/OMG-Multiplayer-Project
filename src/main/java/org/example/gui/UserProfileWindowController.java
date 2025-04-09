package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

public class UserProfileWindowController {

    // Overview Tab
    public Label welcomeLabel;
    public PieChart gameDistributionPieChart;

    // Game Stats Tab
    public Label totalGamesLabel;
    public Label winsLabel;
    public Label lossesLabel;
    public Label winRateStatLabel;
    public PieChart allGamesPieChart;
    public TableView allGamesTable;
    public PieChart ticTacToePieChart;
    public TableView ticTacToeTable;
    public PieChart checkersPieChart;
    public TableView checkersTable;
    public PieChart connect4PieChart;
    public TableView connect4Table;

    // Match History Tab
    public ComboBox gameTypeCombo;
    public ComboBox resultCombo;
    public TextField searchField;
    public ComboBox dateRangeCombo;
    public TableView matchHistoryTable;

    // Settings Tab
    public TextField usernameField;
    public TextField emailField;
    public TextField displayNameField;
    public TextArea bioArea;
    public CheckBox gameInviteCheck;
    public CheckBox rankChangeCheck;
    public CheckBox friendRequestCheck;
    public CheckBox platformNewsCheck;
    public CheckBox emailCheck;
    public CheckBox inAppCheck;
    public CheckBox pushCheck;
    public CheckBox dndCheck;
    public CheckBox showGameHistoryCheck;
    public CheckBox showRankingsCheck;
    public CheckBox showCurrentGameCheck;
    public RadioButton privateRadio;
    public CheckBox achievementCheck;
    public RadioButton publicRadio;
    public RadioButton friendsRadio;
    public RadioButton onlineForAllRadio;
    public RadioButton onlineForFriendsRadio;
    public RadioButton appearOfflineRadio;
    public RadioButton darkThemeRadio;
    public RadioButton lightThemeRadio;
    public RadioButton systemThemeRadio;
    public RadioButton blueColorRadio;
    public RadioButton greenColorRadio;
    public RadioButton orangeColorRadio;
    public RadioButton redColorRadio;
    public RadioButton purpleColorRadio;
    public Slider fontSizeSlider;
    public Label fontSizeValueLabel;
    public CheckBox enableAnimationsCheck;

    /**
     * Initialize method to set up data for charts and tables.
     */
    public void initialize() {
        // Example game distribution data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Tic-Tac-Toe", 42),
                new PieChart.Data("Connect Four", 28),
                new PieChart.Data("Checkers", 16)
        );

        // Set data for the game distribution pie chart in the Overview tab
        if (gameDistributionPieChart != null) {
            gameDistributionPieChart.setData(pieChartData);
        }
    }

    /**
     * Handles the change password action.
     */
    public void handleChangePassword(ActionEvent actionEvent) {
        // Add logic for changing the password here
    }

    /**
     * Handles resetting the theme to default.
     */
    public void handleResetTheme(ActionEvent actionEvent) {
        // Add logic for resetting the theme here
    }

    /**
     * Handles saving settings.
     */
    public void handleSaveSettings(ActionEvent actionEvent) {
        // Add logic for saving settings here
    }
}