package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserProfileWindowController {

    // Overview Tab
    @FXML public Label welcomeLabel;
    @FXML public PieChart gameDistributionPieChart;

    // Game Stats Tab
    @FXML public Label totalGamesLabel;
    @FXML public Label winsLabel;
    @FXML public Label lossesLabel;
    @FXML public Label winRateStatLabel;
    @FXML public PieChart allGamesPieChart;
    @FXML public TableView<GameRecord> allGamesTable;
    @FXML public PieChart ticTacToePieChart;
    @FXML public TableView<GameRecord> ticTacToeTable;
    @FXML public PieChart checkersPieChart;
    @FXML public TableView<GameRecord> checkersTable;
    @FXML public PieChart connect4PieChart;
    @FXML public TableView<GameRecord> connect4Table;

    // Match History Tab
    @FXML public ComboBox<String> gameTypeCombo;
    @FXML public ComboBox<String> resultCombo;
    @FXML public TextField searchField;
    @FXML public ComboBox<String> dateRangeCombo;
    @FXML public TableView<GameRecord> matchHistoryTable;

    // Settings Tab
    @FXML public TextField usernameField, emailField, displayNameField;
    @FXML public TextArea bioArea;
    @FXML public CheckBox gameInviteCheck, rankChangeCheck, friendRequestCheck, platformNewsCheck,
            emailCheck, inAppCheck, pushCheck, dndCheck,
            showGameHistoryCheck, showRankingsCheck, showCurrentGameCheck,
            achievementCheck;
    @FXML public RadioButton privateRadio, publicRadio, friendsRadio,
            onlineForAllRadio, onlineForFriendsRadio, appearOfflineRadio,
            darkThemeRadio, lightThemeRadio, systemThemeRadio,
            blueColorRadio, greenColorRadio, orangeColorRadio,
            redColorRadio, purpleColorRadio;
    @FXML public Slider fontSizeSlider;
    @FXML public Label fontSizeValueLabel;
    @FXML public CheckBox enableAnimationsCheck;

    @FXML
    public void initialize() {
        System.out.println("Initializing UserProfileWindowController...");

        welcomeLabel.setText("Welcome back, Player123!");

        setPieChart(gameDistributionPieChart, "Game Distribution", FXCollections.observableArrayList(
                new PieChart.Data("Tic-Tac-Toe", 42),
                new PieChart.Data("Connect Four", 28),
                new PieChart.Data("Checkers", 16)
        ));

        setPieChart(allGamesPieChart, "All Games", FXCollections.observableArrayList(
                new PieChart.Data("Wins", 70),
                new PieChart.Data("Losses", 30)
        ));

        setPieChart(ticTacToePieChart, "Tic-Tac-Toe", FXCollections.observableArrayList(
                new PieChart.Data("Wins", 25),
                new PieChart.Data("Losses", 10),
                new PieChart.Data("Draws", 7)
        ));

        setPieChart(checkersPieChart, "Checkers", FXCollections.observableArrayList(
                new PieChart.Data("Wins", 10),
                new PieChart.Data("Losses", 5),
                new PieChart.Data("Draws", 1)
        ));

        setPieChart(connect4PieChart, "Connect Four", FXCollections.observableArrayList(
                new PieChart.Data("Wins", 12),
                new PieChart.Data("Losses", 15),
                new PieChart.Data("Draws", 2)
        ));

        setupDemoTable(allGamesTable);
        setupDemoTable(ticTacToeTable);
        setupDemoTable(checkersTable);
        setupDemoTable(connect4Table);
        setupDemoTable(matchHistoryTable);
    }

    private void setPieChart(PieChart chart, String title, ObservableList<PieChart.Data> data) {
        if (chart != null) {
            chart.setTitle(title);
            chart.setData(data);
        }
    }

    private void setupDemoTable(TableView<GameRecord> table) {
        if (table != null) {
            TableColumn<GameRecord, String> dateCol = new TableColumn<>("Date");
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

            TableColumn<GameRecord, String> gameCol = new TableColumn<>("Game");
            gameCol.setCellValueFactory(new PropertyValueFactory<>("game"));

            TableColumn<GameRecord, String> resultCol = new TableColumn<>("Result");
            resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));

            table.getColumns().setAll(dateCol, gameCol, resultCol);
            table.setItems(FXCollections.observableArrayList(
                    new GameRecord("2025-04-01", "Tic-Tac-Toe", "Win"),
                    new GameRecord("2025-04-02", "Connect Four", "Loss")
            ));
        }
    }

    public void handleChangePassword(ActionEvent actionEvent) {
        System.out.println("Change Password clicked.");
    }

    public void handleResetTheme(ActionEvent actionEvent) {
        System.out.println("Resetting theme to default.");
    }

    public void handleSaveSettings(ActionEvent actionEvent) {
        System.out.println("Settings saved!");
    }

    public static class GameRecord {
        private final String date;
        private final String game;
        private final String result;

        public GameRecord(String date, String game, String result) {
            this.date = date;
            this.game = game;
            this.result = result;
        }

        public String getDate() { return date; }
        public String getGame() { return game; }
        public String getResult() { return result; }
    }
}
