package org.example.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
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
    private Map<String, Integer> gameStats;
    private Map<String, Integer> ranks;

    public UserProfileWindow(Stage stage, UserProfile userProfile) {
        this.stage = stage;
        this.userProfile = userProfile;
        initializeMockData();
        initializeUI();
    }

    private void initializeMockData() {
        gameStats = new HashMap<>();
        gameStats.put("Tic-Tac-Toe", 42);
        gameStats.put("Connect Four", 28);
        gameStats.put("Checkers", 16);

        ranks = new HashMap<>();
        ranks.put("Tic-Tac-Toe", 1250);
        ranks.put("Connect Four", 1423);
        ranks.put("Checkers", 1342);
    }

    private void initializeUI() {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #2c3e50;");

        mainLayout.setTop(createHeader());

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

    private Node createOverviewPane() {
        VBox overviewPane = new VBox(20);
        overviewPane.setPadding(new Insets(20));
        overviewPane.setStyle("-fx-background-color: #2c3e50;");

        // Welcome message
        Label welcomeLabel = new Label("Welcome back!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Stats grid
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(10);
        statsGrid.setPadding(new Insets(10, 0, 20, 0));

        // First row - labels
        Label totalGamesLabel = new Label("Total Games");
        totalGamesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");
        GridPane.setConstraints(totalGamesLabel, 0, 0);

        Label winRateLabel = new Label("Win Rate");
        winRateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");
        GridPane.setConstraints(winRateLabel, 1, 0);

        Label highestRankLabel = new Label("Highest Rank");
        highestRankLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");
        GridPane.setConstraints(highestRankLabel, 2, 0);

        Label lastPlayedLabel = new Label("Last Played");
        lastPlayedLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");
        GridPane.setConstraints(lastPlayedLabel, 3, 0);

        // Second row - values
        Label totalGamesValue = new Label("86");
        totalGamesValue.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        GridPane.setConstraints(totalGamesValue, 0, 1);

        Label winRateValue = new Label("62%");
        winRateValue.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        GridPane.setConstraints(winRateValue, 1, 1);

        Label highestRankValue = new Label("Platinum III");
        highestRankValue.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        GridPane.setConstraints(highestRankValue, 2, 1);

        Label lastPlayedValue = new Label("2 hours ago");
        lastPlayedValue.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        GridPane.setConstraints(lastPlayedValue, 3, 1);

        statsGrid.getChildren().addAll(
                totalGamesLabel, winRateLabel, highestRankLabel, lastPlayedLabel,
                totalGamesValue, winRateValue, highestRankValue, lastPlayedValue
        );

        // Game Distribution Section
        TitledPane gameDistributionContainer = new TitledPane();
        gameDistributionContainer.setText("Game Distribution");
        gameDistributionContainer.setCollapsible(false);

        VBox gameDistributionContent = new VBox(20);
        gameDistributionContent.setStyle("-fx-background-color: #2c3e50;");

        // Game Distribution Pie Chart
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

        // Overall Win Rate Pie Chart
        PieChart winRatePieChart = new PieChart();
        winRatePieChart.setTitle("Overall Win Rate");

        Map<String, Integer> overallResults = new HashMap<>();
        int totalWins = 0, totalLosses = 0, totalDraws = 0;

        String[] allMatches = {
                "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                "Draw with PlayerC in Checkers (Feb 8, 2023)",
                "Won against PlayerD in Tic-Tac-Toe (Feb 7, 2023)",
                "Lost against PlayerE in Connect Four (Feb 5, 2023)"
        };

        for (String match : allMatches) {
            if (match.contains("Won")) {
                totalWins++;
            } else if (match.contains("Lost")) {
                totalLosses++;
            } else if (match.contains("Draw")) {
                totalDraws++;
            }
        }

        overallResults.put("Wins", totalWins);
        overallResults.put("Losses", totalLosses);
        overallResults.put("Draws", totalDraws);

        for (Map.Entry<String, Integer> entry : overallResults.entrySet()) {
            winRatePieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        gameDistributionContent.getChildren().addAll(barChart, winRatePieChart);
        gameDistributionContainer.setContent(gameDistributionContent);

        overviewPane.getChildren().addAll(welcomeLabel, statsGrid, gameDistributionContainer);

        return overviewPane;
    }

    private Node createStatsPane() {
        VBox statsPane = new VBox(20);
        statsPane.setPadding(new Insets(20));
        statsPane.setStyle("-fx-background-color: #2c3e50;");

        Label statsTitle = new Label("Game Stats");
        statsTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        TabPane gameStatsTabs = new TabPane();
        gameStatsTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        gameStatsTabs.getTabs().add(new Tab("All Games", createPieChartTab("All Games")));
        gameStatsTabs.getTabs().add(new Tab("Tic-Tac-Toe", createPieChartTab("Tic-Tac-Toe")));
        gameStatsTabs.getTabs().add(new Tab("Connect Four", createPieChartTab("Connect Four")));
        gameStatsTabs.getTabs().add(new Tab("Checkers", createPieChartTab("Checkers")));

        statsPane.getChildren().addAll(statsTitle, gameStatsTabs);

        return statsPane;
    }

    private Node createPieChartTab(String gameType) {
        VBox pieChartTab = new VBox(20);
        pieChartTab.setStyle("-fx-background-color: #2c3e50;");

        Label pieChartTitle = new Label("Win Rate for " + gameType);
        pieChartTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Map<String, Integer> gameResults = new HashMap<>();

        if ("All Games".equals(gameType)) {
            int totalWins = 0, totalLosses = 0, totalDraws = 0;

            String[] allMatches = {
                    "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                    "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                    "Draw with PlayerC in Checkers (Feb 8, 2023)",
                    "Won against PlayerD in Tic-Tac-Toe (Feb 7, 2023)",
                    "Lost against PlayerE in Connect Four (Feb 5, 2023)"
            };

            for (String match : allMatches) {
                if (match.contains("Won")) {
                    totalWins++;
                } else if (match.contains("Lost")) {
                    totalLosses++;
                } else if (match.contains("Draw")) {
                    totalDraws++;
                }
            }

            gameResults.put("Wins", totalWins);
            gameResults.put("Losses", totalLosses);
            gameResults.put("Draws", totalDraws);
        } else {
            int wins = 0, losses = 0, draws = 0;

            String[] allMatches = {
                    "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                    "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                    "Draw with PlayerC in Checkers (Feb 8, 2023)",
                    "Won against PlayerD in Tic-Tac-Toe (Feb 7, 2023)",
                    "Lost against PlayerE in Connect Four (Feb 5, 2023)"
            };

            for (String match : allMatches) {
                if (match.contains(gameType)) {
                    if (match.contains("Won")) {
                        wins++;
                    } else if (match.contains("Lost")) {
                        losses++;
                    } else if (match.contains("Draw")) {
                        draws++;
                    }
                }
            }

            gameResults.put("Wins", wins);
            gameResults.put("Losses", losses);
            gameResults.put("Draws", draws);
        }

        PieChart pieChart = new PieChart();
        pieChart.setTitle("Win Rate for " + gameType);

        for (Map.Entry<String, Integer> entry : gameResults.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChartTab.getChildren().addAll(pieChartTitle, pieChart);

        return pieChartTab;
    }

    private VBox createStatBox(String title, String value, String color) {
        VBox statBox = new VBox(5);
        statBox.setStyle("-fx-background-color: " + color + "; -fx-padding: 10;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        statBox.getChildren().addAll(titleLabel, valueLabel);

        return statBox;
    }

    private Node createMatchHistoryPane() {
        VBox matchHistoryPane = new VBox(20);
        matchHistoryPane.setStyle("-fx-background-color: #2c3e50;");

        Label matchHistoryTitle = new Label("Match History");
        matchHistoryTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        TabPane matchTabs = new TabPane();
        matchTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        matchTabs.getTabs().add(new Tab("All Games", createAllGamesTab()));
        matchTabs.getTabs().add(new Tab("Tic-Tac-Toe", createGameTab("Tic-Tac-Toe")));
        matchTabs.getTabs().add(new Tab("Connect Four", createGameTab("Connect Four")));
        matchTabs.getTabs().add(new Tab("Checkers", createGameTab("Checkers")));

        matchHistoryPane.getChildren().addAll(matchHistoryTitle, matchTabs);

        return matchHistoryPane;
    }

    private Node createAllGamesTab() {
        VBox allGamesTab = new VBox(20);
        allGamesTab.setStyle("-fx-background-color: #2c3e50;");

        Label allGamesTitle = new Label("Recent Matches (Last 20 Played)");
        allGamesTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        ListView<String> matchListView = new ListView<>();
        matchListView.setPrefHeight(400);
        matchListView.setStyle("-fx-control-inner-background: #1a2530; -fx-text-fill: white;");

        String[] allMatches = {
                "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                "Draw with PlayerC in Checkers (Feb 8, 2023)",
                "Won against PlayerD in Tic-Tac-Toe (Feb 7, 2023)",
                "Lost against PlayerE in Connect Four (Feb 5, 2023)"
        };

        matchListView.getItems().addAll(allMatches);

        allGamesTab.getChildren().addAll(allGamesTitle, matchListView);

        return allGamesTab;
    }

    private Node createGameTab(String gameType) {
        VBox gameTab = new VBox(20);
        gameTab.setStyle("-fx-background-color: #2c3e50;");

        Label gameTitle = new Label("Recent Matches in " + gameType);
        gameTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        ListView<String> matchListView = new ListView<>();
        matchListView.setPrefHeight(400);
        matchListView.setStyle("-fx-control-inner-background: #1a2530; -fx-text-fill: white;");

        String[] allMatches = {
                "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                "Draw with PlayerC in Checkers (Feb 8, 2023)",
                "Won against PlayerD in Tic-Tac-Toe (Feb 7, 2023)",
                "Lost against PlayerE in Connect Four (Feb 5, 2023)"
        };

        for (String match : allMatches) {
            if (match.contains(gameType)) {
                matchListView.getItems().add(match);
            }
        }

        gameTab.getChildren().addAll(gameTitle, matchListView);

        return gameTab;
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

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1a2530; -fx-border-width: 2px; -fx-border-radius: 4;");

        // Avatar placeholder
        Region avatarPlaceholder = new Region();
        avatarPlaceholder.setPrefSize(60, 60);
        avatarPlaceholder.setStyle("-fx-background-color: #3498db; -fx-background-radius: 30;");

        // User info section
        VBox userInfo = new VBox(5);
        Label nameLabel = new Label(userProfile.getUsername());
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label joinDateLabel = new Label("Member since: " + (userProfile.getJoinDate() != null ? userProfile.getJoinDate() : "null"));
        joinDateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");

        Label statusLabel = new Label("Online");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #2ecc71;");

        userInfo.getChildren().addAll(nameLabel, joinDateLabel, statusLabel);

        // Rank info section - made more compact like the reference
        VBox rankInfo = new VBox(2);
        rankInfo.setAlignment(Pos.CENTER_RIGHT);
        rankInfo.setPadding(new Insets(0, 20, 0, 20));

        Label rankLabel = new Label("Gold");
        rankLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #f39c12;");

        Label ratingLabel = new Label("1342");
        ratingLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label ratingTextLabel = new Label("Overall rating");
        ratingTextLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #bdc3c7;");

        rankInfo.getChildren().addAll(rankLabel, ratingLabel, ratingTextLabel);

        // Welcome back message
        Label welcomeLabel = new Label("Welcome back!");
        welcomeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-padding: 0 20 0 20;");

        // Spacer to push content to the left
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(avatarPlaceholder, userInfo, spacer, welcomeLabel, rankInfo);

        return header;
    }

    public void show() {
        stage.show();
    }
}