package org.example.gui;

import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

public class UserProfileWindow {
    public Label welcomeLabel;
    public BarChart gameDistributionChart;
    public Label totalGamesLabel;
    public Label winsLabel;
    public Label lossesLabel;
    public Label winRateStatLabel;
    public PieChart gamesPieChart;
    public TableView gameStatsTable;
    public ComboBox gameSelector;
    public RadioButton weekBtn;
    public RadioButton monthBtn;
    public RadioButton allTimeBtn;
    public LineChart performanceChart;
    public ComboBox gameTypeCombo;
    public ComboBox resultCombo;
    public ComboBox dateRangeCombo;
    public TextField searchField;
    public TableView matchHistoryTable;
    public TextField usernameField;
    public TextField emailField;
    public TextField displayNameField;
    public TextArea bioArea;
    public CheckBox gameInviteCheck;
    public CheckBox friendRequestCheck;
    public CheckBox achievementCheck;
    public CheckBox rankChangeCheck;
    public CheckBox platformNewsCheck;
    public CheckBox inAppCheck;
    public CheckBox emailCheck;
    public CheckBox pushCheck;
    public CheckBox dndCheck;
    public CheckBox showGameHistoryCheck;
    public CheckBox showRankingsCheck;
    public RadioButton appearOfflineRadio;
    public RadioButton onlineForAllRadio;
    public RadioButton onlineForFriendsRadio;
    public RadioButton publicRadio;
    public RadioButton friendsRadio;
    public RadioButton privateRadio;
    public CheckBox showCurrentGameCheck;
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

    public UserProfileWindow() {
    }

    public UserProfileWindow(Stage primaryStage, UserProfile userProfile) {
    }

    public void handleChangePassword(ActionEvent actionEvent) {
    }

    public void handleResetTheme(ActionEvent actionEvent) {
    }

    public void handleSaveSettings(ActionEvent actionEvent) {
    }
}