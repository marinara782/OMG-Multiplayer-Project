package org.example.gui;

import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

public class UserProfileWindowController {
    public Label welcomeLabel;
    public PieChart gameDistributionPieChart;
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
    public ComboBox gameTypeCombo;
    public ComboBox resultCombo;
    public TextField searchField;
    public ComboBox dateRangeCombo;
    public TableView matchHistoryTable;
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

    public void handleChangePassword(ActionEvent actionEvent) {
    }

    public void handleResetTheme(ActionEvent actionEvent) {
    }

    public void handleSaveSettings(ActionEvent actionEvent) {

    }
}
