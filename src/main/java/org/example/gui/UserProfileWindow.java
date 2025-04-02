package org.example.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

import java.util.HashMap;
import java.util.Map;

/*UserProfileWindow class represents the GUI window for displaying user profile information
 including game statistics, match history, and settings.
 */
public class UserProfileWindow {
    // UI components and data structures
    private Stage stage;
    private Scene scene;
    private BorderPane mainLayout;
    private UserProfile userProfile;
    private TabPane tabPane;
    private Map<String, Integer> gameStats;  // Stores game statistics data
    private Map<String, Integer> ranks;      // Stores player ranks for different games

    /*Constructor for UserProfileWindow @param stage The primary stage for this window
     * @param userProfile The user profile data to display
     */
    public UserProfileWindow(Stage stage, UserProfile userProfile) {
        this.stage = stage;
        this.userProfile = userProfile;
        initializeMockData();  // Initialize sample data for demonstration
        initializeUI();        // Set up the user interface
    }

    //Initializes mock data for demonstration purposes

    private void initializeMockData() {
        // Initialize game statistics with sample data
        gameStats = new HashMap<>();
        gameStats.put("Tic-Tac-Toe", 42);
        gameStats.put("Connect Four", 28);
        gameStats.put("Checkers", 16);

        // Initialize ranks with sample data
        ranks = new HashMap<>();
        ranks.put("Tic-Tac-Toe", 1250);
        ranks.put("Connect Four", 1423);
        ranks.put("Checkers", 1342);
    }

    //Initializes the main user interface components
    private void initializeUI() {
        // Set up the main layout container
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #2c3e50;");

        // Add header to the top of the layout
        mainLayout.setTop(createHeader());

        // Create tab pane for different sections of the profile
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create and add all tabs
        Tab overviewTab = new Tab("Overview", createOverviewPane());
        Tab statsTab = new Tab("Game Stats", createStatsPane());
        Tab matchHistoryTab = new Tab("Match History", createMatchHistoryPane());
        Tab settingsTab = new Tab("Settings", createSettingsPane());

        tabPane.getTabs().addAll(overviewTab, statsTab, matchHistoryTab, settingsTab);

        // Add tab pane to center of main layout
        mainLayout.setCenter(tabPane);

        // Set up the scene and stage properties
        scene = new Scene(mainLayout, 900, 700);
        stage.setTitle("User Profile - OMG Platform");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
    }

    /*
     * Creates the game statistics pane with pie charts
     * @return Node containing the stats UI
     */
    private Node createStatsPane() {
        VBox statsPane = new VBox(20);
        statsPane.setPadding(new Insets(20));
        statsPane.setStyle("-fx-background-color: #2c3e50;");

        // Title label for stats section
        Label statsTitle = new Label("Game Stats");
        statsTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // Tab pane for different game statistics
        TabPane gameStatsTabs = new TabPane();
        gameStatsTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add tabs for each game type
        gameStatsTabs.getTabs().add(new Tab("All Games", createPieChartTab("All Games")));
        gameStatsTabs.getTabs().add(new Tab("Tic-Tac-Toe", createPieChartTab("Tic-Tac-Toe")));
        gameStatsTabs.getTabs().add(new Tab("Connect Four", createPieChartTab("Connect Four")));
        gameStatsTabs.getTabs().add(new Tab("Checkers", createPieChartTab("Checkers")));

        statsPane.getChildren().addAll(statsTitle, gameStatsTabs);

        return statsPane;
    }

    /*
     * Creates a pie chart tab for a specific game type
     * @param gameType The game type to display statistics for
     * @return Node containing the pie chart UI
     */
    private Node createPieChartTab(String gameType) {
        VBox pieChartTab = new VBox(20);
        pieChartTab.setStyle("-fx-background-color: #2c3e50;");

        // Title for the pie chart
        Label pieChartTitle = new Label("Win Rate for " + gameType);
        pieChartTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        // Initialize game results data structure
        Map<String, Integer> gameResults = new HashMap<>();

        // Process data differently for "All Games" vs individual games
        if ("All Games".equals(gameType)) {
            // Aggregate statistics for all games
            int totalWins = 0, totalLosses = 0, totalDraws = 0;

            // Sample match data (would normally come from database)
            String[] allMatches = {
                    "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                    "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                    "Draw with PlayerC in Checkers (Feb 8, 2023)",
                    "Won against PlayerD in Tic-Tac-Toe (Feb 7, 2023)",
                    "Lost against PlayerE in Connect Four (Feb 5, 2023)"
            };

            // Count wins, losses, and draws
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
            // Statistics for a specific game type
            int wins = 0, losses = 0, draws = 0;

            String[] allMatches = {
                    "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                    "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                    "Draw with PlayerC in Checkers (Feb 8, 2023)",
                    "Won against PlayerD in Tic-Tac-Toe (Feb 7, 2023)",
                    "Lost against PlayerE in Connect Four (Feb 5, 2023)"
            };

            // Count only matches for the specified game type
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

        // Create and configure the pie chart
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Win Rate for " + gameType);

        // Add data to the pie chart
        for (Map.Entry<String, Integer> entry : gameResults.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChartTab.getChildren().addAll(pieChartTitle, pieChart);

        return pieChartTab;
    }

    /*
     * Creates the overview pane with summary statistics
     * @return Node containing the overview UI
     */
    private Node createOverviewPane() {
        VBox overviewPane = new VBox(20);
        overviewPane.setPadding(new Insets(20));
        overviewPane.setStyle("-fx-background-color: #2c3e50;");

        // Create summary statistics boxes
        HBox summary = new HBox(20);
        summary.setAlignment(Pos.CENTER);

        summary.getChildren().addAll(
                createStatBox("Total Games", "86", "#3498db"),
                createStatBox("Win Rate", "62%", "#2ecc71"),
                createStatBox("Highest Rank", "Gold II", "#f39c12")
        );

        // Container for game distribution chart
        TitledPane chartContainer = new TitledPane();
        chartContainer.setText("Game Distribution");
        chartContainer.setContent(createStatsPane());

        overviewPane.getChildren().addAll(summary, chartContainer);
        return overviewPane;
    }

    /*
     * Creates a styled statistics box
     * @param title The title/label for the stat
     * @param value The value to display
     * @param color The background color
     * @return VBox containing the styled stat box
     */
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

    /*
     * Creates the match history pane with tabs for different games
     * @return Node containing the match history UI
     */
    private Node createMatchHistoryPane() {
        VBox matchHistoryPane = new VBox(20);
        matchHistoryPane.setStyle("-fx-background-color: #2c3e50;");

        Label matchHistoryTitle = new Label("Match History");
        matchHistoryTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // Tab pane for different game histories
        TabPane matchTabs = new TabPane();
        matchTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add tabs for each game type
        matchTabs.getTabs().add(new Tab("All Games", createAllGamesTab()));
        matchTabs.getTabs().add(new Tab("Tic-Tac-Toe", createGameTab("Tic-Tac-Toe")));
        matchTabs.getTabs().add(new Tab("Connect Four", createGameTab("Connect Four")));
        matchTabs.getTabs().add(new Tab("Checkers", createGameTab("Checkers")));

        matchHistoryPane.getChildren().addAll(matchHistoryTitle, matchTabs);

        return matchHistoryPane;
    }

    /*
     * Creates the tab showing all games' match history
     * @return Node containing the all games history UI
     */
    private Node createAllGamesTab() {
        VBox allGamesTab = new VBox(20);
        allGamesTab.setStyle("-fx-background-color: #2c3e50;");

        Label allGamesTitle = new Label("Recent Matches (Last 20 Played)");
        allGamesTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        // List view to display match history
        ListView<String> matchListView = new ListView<>();
        matchListView.setPrefHeight(400);
        matchListView.setStyle("-fx-control-inner-background: #1a2530; -fx-text-fill: white;");

        // Sample match data
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

    /*
     * Creates a tab for a specific game's match history
     * @param gameType The game type to show history for
     * @return Node containing the game-specific history UI
     */
    private Node createGameTab(String gameType) {
        VBox gameTab = new VBox(20);
        gameTab.setStyle("-fx-background-color: #2c3e50;");

        Label gameTitle = new Label("Recent Matches in " + gameType);
        gameTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        ListView<String> matchListView = new ListView<>();
        matchListView.setPrefHeight(400);
        matchListView.setStyle("-fx-control-inner-background: #1a2530; -fx-text-fill: white;");

        // Sample match data
        String[] allMatches = {
                "Won against PlayerA in Tic-Tac-Toe (Feb 12, 2023)",
                "Lost against PlayerB in Connect Four (Feb 10, 2023)",
                "Draw with PlayerC in Checkers (Feb 8, 2023)",
                "Won against PlayerD in Tic-Tac-Toe (Feb 7, 2023)",
                "Lost against PlayerE in Connect Four (Feb 5, 2023)"
        };

        // Filter matches for the specified game type
        for (String match : allMatches) {
            if (match.contains(gameType)) {
                matchListView.getItems().add(match);
            }
        }

        gameTab.getChildren().addAll(gameTitle, matchListView);

        return gameTab;
    }

    /*
     * Creates the settings pane with user configuration options
     * @return Node containing the settings UI
     */
    private Node createSettingsPane() {
        VBox settingsPane = new VBox(20);
        settingsPane.setPadding(new Insets(20));
        settingsPane.setStyle("-fx-background-color: #2c3e50;");

        Label settingsTitle = new Label("Settings");
        settingsTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // Settings buttons
        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        Button notificationSettingsButton = new Button("Notification Settings");
        notificationSettingsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        settingsPane.getChildren().addAll(settingsTitle, changePasswordButton, notificationSettingsButton);

        return settingsPane;
    }

    /*
     * Creates the header section with user profile information
     * @return HBox containing the header UI
     */
    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1a2530;");

        // Avatar placeholder
        Region avatarPlaceholder = new Region();
        avatarPlaceholder.setPrefSize(80, 80);
        avatarPlaceholder.setStyle("-fx-background-color: #3498db; -fx-background-radius: 40;");

        // User information section
        VBox userInfo = new VBox(5);
        Label nameLabel = new Label(userProfile.getUsername());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label joinDateLabel = new Label("Member since: " + userProfile.getJoinDate());
        joinDateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        Label statusLabel = new Label(userProfile.getStatus());
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2ecc71;");

        userInfo.getChildren().addAll(nameLabel, joinDateLabel, statusLabel);

        // Rank information section
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

        // Spacer to push close button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        closeButton.setOnAction(e -> stage.close());

        header.getChildren().addAll(avatarPlaceholder, userInfo, rankInfo, spacer, closeButton);

        return header;
    }

    //Displays the user profile window
    public void show() {
        stage.show();
    }
}