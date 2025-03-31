package org.example.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class UserProfileWindow {
    private Stage stage;
    private Scene scene;
    private BorderPane mainLayout;
    private UserProfile userProfile;
    private TabPane tabPane;

    // Mock data for the profile
    private Map<String, Integer> gameStats;
    private Map<String, Integer> ranks;

    public UserProfileWindow(Stage stage, UserProfile userProfile) {
        this.stage = stage;
        this.userProfile = userProfile;
        initializeMockData();
        initializeUI();
    }

    private void initializeMockData() {
        // Mock game statistics
        gameStats = new HashMap<>();
        gameStats.put("Tic-Tac-Toe", 42);
        gameStats.put("Connect Four", 28);
        gameStats.put("Checkers", 16);

        // Mock ranks
        ranks = new HashMap<>();
        ranks.put("Tic-Tac-Toe", 1250);
        ranks.put("Connect Four", 1423);
        ranks.put("Checkers", 1342);
    }

    private void initializeUI() {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #2c3e50;");

        // Header with user info
        HBox header = createHeader();
        mainLayout.setTop(header);

        // Main content with tabs
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab overviewTab = new Tab("Overview", createOverviewPane());
        Tab statsTab = new Tab("Game Stats", createStatsPane());
        Tab matchHistoryTab = new Tab("Match History", createMatchHistoryPane());
        Tab settingsTab = new Tab("Settings", createSettingsPane());

        tabPane.getTabs().addAll(overviewTab, statsTab, matchHistoryTab, settingsTab);

        mainLayout.setCenter(tabPane);

        scene = new Scene(mainLayout, 900, 700);
        stage.setTitle("User Profile - OMG Platform");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
    }

    private Node createSettingsPane() {
        VBox settingsPane = new VBox(20);
        settingsPane.setPadding(new Insets(20));
        settingsPane.setStyle("-fx-background-color: #2c3e50;");

        Label settingsTitle = new Label("Settings");
        settingsTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        Button notificationSettingsButton = new Button("Notification Settings");
        notificationSettingsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        settingsPane.getChildren().addAll(settingsTitle, changePasswordButton, notificationSettingsButton);

        return settingsPane;
    }

    private Node createMatchHistoryPane() {
        VBox matchHistoryPane = new VBox(20);
        matchHistoryPane.setPadding(new Insets(20));
        matchHistoryPane.setStyle("-fx-background-color: #2c3e50;");

        Label matchHistoryTitle = new Label("Match History");
        matchHistoryTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // Add logic to fetch and display match history here
        ListView<String> matchListView = new ListView<>();
        matchListView.getItems().addAll(
                "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                "Draw with PlayerC in Checkers (Feb 8, 2023)"
        );
        matchListView.setStyle("-fx-control-inner-background: #1a2530; -fx-text-fill: white;");

        matchHistoryPane.getChildren().addAll(matchHistoryTitle, matchListView);

        return matchHistoryPane;
    }

    private Node createStatsPane() {
        VBox statsPane = new VBox(20);
        statsPane.setPadding(new Insets(20));
        statsPane.setStyle("-fx-background-color: #2c3e50;");

        Label statsTitle = new Label("Game Stats");
        statsTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Games Played by Type");
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : gameStats.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);

        statsPane.getChildren().addAll(statsTitle, barChart);

        return statsPane;
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1a2530;");

        // User avatar placeholder
        Region avatarPlaceholder = new Region();
        avatarPlaceholder.setPrefSize(80, 80);
        avatarPlaceholder.setStyle("-fx-background-color: #3498db; -fx-background-radius: 40;");

        // User info
        VBox userInfo = new VBox(5);
        Label nameLabel = new Label(userProfile.getUsername());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label joinDateLabel = new Label("Member since: " + userProfile.getJoinDate());
        joinDateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        Label statusLabel = new Label(userProfile.getStatus());
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2ecc71;");

        userInfo.getChildren().addAll(nameLabel, joinDateLabel, statusLabel);

        // Rank badge
        VBox rankInfo = new VBox(5);
        rankInfo.setAlignment(Pos.CENTER);
        rankInfo.setPadding(new Insets(0, 0, 0, 40));

        Label rankLabel = new Label("Gold");
        rankLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #f39c12;");

        Label ratingLabel = new Label("1342");
        ratingLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label ratingTextLabel = new Label("Overall Rating");
        ratingTextLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");

        rankInfo.getChildren().addAll(rankLabel, ratingLabel, ratingTextLabel);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        closeButton.setOnAction(e -> stage.close());

        header.getChildren().addAll(avatarPlaceholder, userInfo, rankInfo, spacer, closeButton);

        return header;
    }

    private VBox createOverviewPane() {
        VBox overviewPane = new VBox(20);
        overviewPane.setPadding(new Insets(20));
        overviewPane.setStyle("-fx-background-color: #2c3e50;");

        // Summary section
        HBox summary = new HBox(20);
        summary.setAlignment(Pos.CENTER);

        // Total games stat
        VBox totalGamesBox = createStatBox("Total Games", "86", "#3498db");

        // Win rate stat
        VBox winRateBox = createStatBox("Win Rate", "62%", "#2ecc71");

        // Highest rank stat
        VBox highestRankBox = createStatBox("Highest Rank", "Gold II", "#f39c12");

        summary.getChildren().addAll(totalGamesBox, winRateBox, highestRankBox);

        // Game distribution chart
        TitledPane chartContainer = new TitledPane();
        chartContainer.setText("Game Distribution");
        chartContainer.setCollapsible(false);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Games Played by Type");
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : gameStats.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
        chartContainer.setContent(barChart);

        // Recent achievements
        TitledPane achievementsContainer = new TitledPane();
        achievementsContainer.setText("Recent Achievements");
        achievementsContainer.setCollapsible(false);

        VBox achievementsList = new VBox(10);
        achievementsList.setPadding(new Insets(10));

        String[] achievements = {
                "First Win in Connect Four",
                "5-Win Streak in Tic-Tac-Toe",
                "Reached Gold Rank",
                "Played 50 Games"
        };

        for (String achievement : achievements) {
            HBox achievementRow = new HBox(10);
            achievementRow.setAlignment(Pos.CENTER_LEFT);

            // Achievement icon placeholder
            Region iconPlaceholder = new Region();
            iconPlaceholder.setPrefSize(30, 30);
            iconPlaceholder.setStyle("-fx-background-color: #f39c12; -fx-background-radius: 15;");

            Label achievementLabel = new Label(achievement);
            achievementLabel.setStyle("-fx-text-fill: white;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label dateLabel = new Label("Feb 12, 2023");
            dateLabel.setStyle("-fx-text-fill: #bdc3c7;");

            achievementRow.getChildren().addAll(iconPlaceholder, achievementLabel, spacer, dateLabel);
            achievementsList.getChildren().add(achievementRow);
        }

        achievementsContainer.setContent(achievementsList);

        overviewPane.getChildren().addAll(summary, chartContainer, achievementsContainer);

        return overviewPane;
    }

    private VBox createStatBox(String title, String value, String color) {
        VBox statBox = new VBox(5);
        statBox.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 5; -fx-padding: 10;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        statBox.getChildren().addAll(titleLabel, valueLabel);

        return statBox;
    }

    public void show() {
        stage.show();
    }
}